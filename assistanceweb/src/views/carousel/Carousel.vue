<template>
	<section>
    <div v-show="show=='list'">
      <!--工具条-->
      <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
        <el-form :inline="true" :model="queryForm">
          <el-form-item>
            <el-input v-model="queryForm.name" placeholder="轮播图名称"></el-input>
          </el-form-item>
          <el-form-item>
            <el-select v-model="queryForm.status" placeholder="请选择轮播图状态" clearable>
              <el-option
                v-for="(value,key) in statuss"
                :key="key"
                :label="value"
                :value="key">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" v-on:click="getTableData">查询</el-button>
            <el-button type="primary" @click="handleAdd" v-hasPermission="'carousel.add'">新增</el-button>
          </el-form-item>
        </el-form>
      </el-col>

      <!--列表-->
      <el-table :data="tableData" stripe highlight-current-row v-loading="listLoading" @sort-change="sortChanged" style="width: 100%;" :default-sort = "{prop: 'createTime', order: 'descending'}" :empty-text="message">
        <el-table-column prop="name" label="轮播图名称" width="150" show-overflow-tooltip>
        </el-table-column>
        <el-table-column prop="imageUrl" label="轮播图片" width="100">
          <template slot-scope="scope">
            <img class="img" v-lazy = "scope.row.imageUrl" width="40" height="40" @click="handlePicture(scope.row.imageUrl)"/>
          </template>
        </el-table-column>
        <el-table-column prop="path" label="跳转地址" width="100" show-overflow-tooltip>
        </el-table-column>
        <el-table-column prop="statusName" label="轮播图状态" width="120" sortable='custom'>
        </el-table-column>
        <el-table-column prop="createTime" label="发布时间" width="150" sortable='custom' show-overflow-tooltip>
        </el-table-column>
        <el-table-column prop="clicked" label="点击次数" width="100">
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button size="small" @click="handleEdit(scope.$index, scope.row)" v-hasPermission="'carousel.update'">编辑</el-button>
            <el-button type="primary" size="small" @click="handleUp(scope.$index, scope.row)" v-if="scope.row.status == 'draft'" v-hasPermission="'carousel.up'">上架</el-button>
            <el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)" v-if="scope.row.status == 'draft' || scope.row.status == 'down'" v-hasPermission="'carousel.delete'">删除</el-button>
            <el-button type="warning" size="small" @click="handleDown(scope.$index, scope.row)" v-if="scope.row.status == 'on'" v-hasPermission="'carousel.down'">下架</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!--工具条-->
      <el-col :span="24" class="toolbar">
        <el-pagination layout="total, prev, pager, next" @current-change="handleCurrentChange" :page-size="pageSize" :total="total" prev-text="上一页" next-text="下一页" background style="float:right;">
        </el-pagination>
      </el-col>
    </div>

    <div v-show="show=='add' || show=='update'">
      <el-row>
        <el-col :span="16">
          <el-form :model="carouselForm" label-width="100px" :rules="carouselFormRules" ref="carouselForm">
            <el-form-item label="轮播图片" required>
                <el-upload
                  list-type="picture-card"
                  :action="action"
                  :on-preview="handlePictureCardPreview"
                  :file-list="fileList"
                  :on-success="handleSuccess"
                  :on-remove="handleRemove"
                  :data="{type: 'image', size: '700x300'}"
                  :limit="1">
                <i class="el-icon-plus"></i>
                </el-upload>
            </el-form-item>
            <el-form-item label="轮播图名称" prop="name">
              <el-input v-model="carouselForm.name" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="跳转地址">
              <el-input v-model="carouselForm.path" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="轮播详情">
              <div id="description"></div>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" size="small" @click="handleSubmit" :loading="submitLoading">提交</el-button>
              <el-button size="small" @click="back">取消</el-button>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
    </div>

    <el-dialog :visible.sync="dialogVisible">
      <img width="100%" :src="dialogImageUrl">
    </el-dialog>

	</section>
</template>

<script>
	export default {
		data() {
			return {
				queryForm: {
          name: '',
          status: ''
        },
        statuss: {'draft': '未上架', 'on': '上架中', 'down': '已下架'},
				tableData: [],
        total: 0,
        pages: 0,
        page: 1,
        pageSize: 8,
        sort: 'createTime',
        order: 'desc',
        message: '',
        listLoading: false,

        dialogVisible: false,
        dialogImageUrl: "",

        show: 'list',

        submitLoading: false,

        editor: undefined,

				carouselFormRules: {
					name: [
						{ required: true, message: '请输入轮播图名称', trigger: 'blur' }
          ]
				},
				carouselForm: {
          name: '',
          path: '',
          imageUrl: '',
          plot: ''
        },

        fileList: [],
        imageUrlList: [],
        service: ''
			}
		},
		methods: {
			handleCurrentChange(val) {
				this.page = val;
				this.getTableData();
      },
      handlePicture: function(url){
        this.dialogImageUrl = url
        this.dialogVisible = true
      },
      handlePictureCardPreview: function(file){
        this.dialogImageUrl = file.url;
        this.dialogVisible = true;
      },
			//获取轮播图列表
			getTableData() {
				let para = {
          name: this.queryForm.name,
          status: this.queryForm.status,
          page: this.page,
          pageSize: this.pageSize,
          sort: this.sort,
          order: this.order
				};
        this.listLoading = true;
				this.$ajax.get('/carousel/page',para).then((res) => {
          this.listLoading = false;
          if(res.success){
            this.page = res.page;
            this.pageSize = res.pageSize;
            this.total = res.total;
            this.pages = res.pages;
            this.tableData = res.data;
          }else{
            this.tableData = [];
            this.page = 0;
            this.total = 0;
            this.pages = 0;
            this.message = res.message;
          }
				});
			},
			//删除
			handleDel: function (index, row) {
				this.$confirm('确认删除该轮播图吗?', '提示', {
					type: 'warning'
				}).then(() => {
					this.listLoading = true;
					this.$ajax.post('/carousel/delete/'+row.id).then((res) => {
            this.listLoading = false;
            if(res.success){
              this.$message({
                message: '删除成功',
                type: 'success'
              });
              this.getTableData();
            }
					});
				}).catch(() => {

				});
			},
			//显示编辑界面
			handleEdit: function (index, row) {
        this.imageUrlList = []
        this.$ajax.get('/carousel/get/'+row.id).then(res=>{
          if(res.success){
            this.carouselForm = Object.assign({}, res.data)
            this.show = 'update'
            this.fileList = []
            this.fileList.push({url: res.data.imageUrl})
            if(this.editor==undefined){
              var self = this
              var E = window.wangEditor
              this.editor = new E('#description')
              this.editor.customConfig.uploadImgServer = this.action
              this.editor.customConfig.uploadImgMaxSize = 10 * 1024 * 1024
              this.editor.customConfig.uploadFileName = 'file'
              this.editor.customConfig.uploadImgHooks = {
                customInsert: function(insertImg, res, editor){
                  if(res.success){
                    let data = res.data || []
                    data.forEach(function (link) {
                      insertImg(self.service+link)
                    })
                  }
                }
              }
              this.editor.create()
            }
            if(this.carouselForm.plot!=undefined || this.carouselForm.plot!=''){
              this.editor.txt.html(this.carouselForm.plot)
            }
          }
        })
      },
			//上架
			handleUp: function (index, row) {
        this.listLoading = true;
        this.$ajax.post('/carousel/up/'+row.id).then((res)=>{
          this.listLoading = false;
          if(res.success){
            this.$message.success('上架成功')
            this.getTableData()
          }
        })
      },
			//下架
			handleDown: function (index, row) {
        this.listLoading = true;
        this.$ajax.post('/carousel/down/'+row.id).then((res)=>{
          this.listLoading = false;
          if(res.success){
            this.$message.success('下架成功')
            this.getTableData()
          }
        })
			},
			//显示新增界面
			handleAdd: function () {
				this.carouselForm = {
          name: '',
          path: '',
          imageUrl: '',
          plot: ''
        }
        this.fileList = []
        this.imageUrlList = []
        this.show = 'add'
        if(this.editor==undefined){
          var self = this
          var E = window.wangEditor
          this.editor = new E('#description')
          this.editor.customConfig.uploadImgServer = this.action
          this.editor.customConfig.uploadImgMaxSize = 10 * 1024 * 1024
          this.editor.customConfig.uploadFileName = 'file'
          this.editor.customConfig.uploadImgHooks = {
            customInsert: function(insertImg, res, editor){
              if(res.success){
                let data = res.data || []
                data.forEach(function (link) {
                  insertImg(self.service+link)
                })
              }
            }
          }
          this.editor.create()
        }
			},
      sortChanged: function(column){
        this.sort = column.prop!=null?column.prop.replace('Name',''):''
        this.order = (column.order === 'ascending')?'asc':'desc'
        this.getTableData()
      },
      handleSuccess: function(response, file, fileList){
        if(response.success){
          this.imageUrlList = []
          fileList.forEach(element => {
            if(element.response === undefined){
              this.imageUrlList.push(element.url)
            }else{
              this.imageUrlList.push(this.service+element.response.data[0])
            }
          });
        }
      },
      handleRemove: function(file, fileList){
        this.imageUrlList = []
        fileList.forEach(element => {
          if(element.response === undefined){
            this.imageUrlList.push(element.url)
          }else{
            this.imageUrlList.push(this.service+element.response.data[0])
          }
        });
      },
      handleSubmit: function(){
        if(this.imageUrlList.length>0){
          this.carouselForm.imageUrl = this.imageUrlList[0]
        }
        if(this.editor){
          this.carouselForm.plot = this.editor.txt.html()
        }
        this.$refs.carouselForm.validate((valid) => {
          if (valid) {
            this.$confirm('确认提交吗？', '提示', {}).then(() => {
              this.submitLoading = true;
              let para = Object.assign({}, this.carouselForm);
              this.$ajax.post('/carousel/'+this.show,para).then((res) => {
                this.submitLoading = false
                if(res.success){
                  this.$message({
                    message: '提交成功',
                    type: 'success'
                  });
                  this.show = 'list'
                  this.getTableData()
                }
              });
            });
          }
        });
      },
      back: function(){
        this.show = 'list'
      }
    },
    mounted(){
      this.service = this.$imageService
    },
    computed: {
      action: function(){
        return this.service + "/file/up"
      }
    }
	}

</script>

<style scoped lang="scss">
.img{
  cursor: pointer;
}
</style>
