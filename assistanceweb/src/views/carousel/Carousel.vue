<template>
	<section>
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

    <!--新增界面-->
    <el-dialog title="新增" :visible.sync="addFormVisible" :close-on-click-modal="false">
      <el-form :model="addForm" label-width="100px" :rules="addFormRules" ref="addForm">
        <el-form-item label="轮播图片" required>
            <el-upload
              list-type="picture-card"
              :action="action"
              :on-preview="handlePictureCardPreview"
              :file-list="fileList"
              :on-success="handleSuccess"
              :on-remove="handleRemove"
              :data="{type: 'image', size: '700x300'}">
              <i class="el-icon-plus"></i>
            </el-upload>
        </el-form-item>
        <el-form-item label="轮播图名称" prop="name">
					<el-input v-model="addForm.name" auto-complete="off"></el-input>
				</el-form-item>
        <el-form-item label="跳转地址">
					<el-input v-model="addForm.path" auto-complete="off"></el-input>
				</el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="addFormVisible = false">取消</el-button>
        <el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
      </div>
    </el-dialog>

    <!--编辑界面-->
    <el-dialog title="编辑" :visible.sync="editFormVisible" :close-on-click-modal="false">
      <el-form :model="editForm" label-width="100px" :rules="editFormRules" ref="editForm">
				<el-form-item label="轮播图片" required>
            <el-upload
              list-type="picture-card"
              :action="action"
              :on-preview="handlePictureCardPreview"
              :file-list="fileList"
              :on-success="handleSuccess"
              :on-remove="handleRemove"
              :data="{type: 'image', size: '700x300'}">
            <i class="el-icon-plus"></i>
            </el-upload>
        </el-form-item>
        <el-form-item label="轮播图名称" prop="name">
					<el-input v-model="editForm.name" auto-complete="off"></el-input>
				</el-form-item>
        <el-form-item label="跳转地址">
					<el-input v-model="editForm.path" auto-complete="off"></el-input>
				</el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="editFormVisible = false">取消</el-button>
        <el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
      </div>
    </el-dialog>

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

        editFormVisible: false,//编辑界面是否显示
				editLoading: false,
				editFormRules: {
					name: [
						{ required: true, message: '请输入轮播图名称', trigger: 'blur' }
					]
				},
				//编辑界面数据
				editForm: {
          id: 0,
          name: '',
          path: '',
          imageUrl: ''
				},

				addFormVisible: false,//新增界面是否显示
				addLoading: false,
				addFormRules: {
					name: [
						{ required: true, message: '请输入轮播图名称', trigger: 'blur' }
          ]
				},
				//新增界面数据
				addForm: {
          name: '',
          path: '',
          imageUrl: ''
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
        var _this = this;
				let para = {
          name: _this.queryForm.name,
          status: _this.queryForm.status,
          page: _this.page,
          pageSize: _this.pageSize,
          sort: _this.sort,
          order: _this.order
				};
        _this.listLoading = true;
				_this.$ajax.get('/carousel/page',para).then((res) => {
          _this.listLoading = false;
          if(res.success){
            _this.page = res.page;
            _this.pageSize = res.pageSize;
            _this.total = res.total;
            _this.pages = res.pages;
            _this.tableData = res.data;
          }else{
            _this.tableData = [];
            _this.page = 0;
            _this.total = 0;
            _this.pages = 0;
            _this.message = res.message;
          }
				});
			},
			//删除
			handleDel: function (index, row) {
        var _this = this;
				_this.$confirm('确认删除该轮播图吗?', '提示', {
					type: 'warning'
				}).then(() => {
					_this.listLoading = true;
					_this.$ajax.post('/carousel/delete/'+row.id).then((res) => {
            _this.listLoading = false;
            if(res.success){
              _this.$message({
                message: '删除成功',
                type: 'success'
              });
              _this.getTableData();
            }
					});
				}).catch(() => {

				});
			},
			//显示编辑界面
			handleEdit: function (index, row) {
        this.imageUrlList = []
        this.editLoading = false
        this.editFormVisible = true
        this.$ajax.get('/carousel/get/'+row.id).then(res=>{
          if(res.success){
            this.editForm = Object.assign({}, res.data.carouselBean);
            this.fileList = []
            this.fileList.push({url: res.data.carouselBean.imageUrl})
            this.editFormVisible = true;
          }
        })
      },
			//上架
			handleUp: function (index, row) {
        var _this = this
        _this.listLoading = true;
        _this.$ajax.post('/carousel/up/'+row.id).then(function(res){
          _this.listLoading = false;
          if(res.success){
            _this.$message.success('上架成功')
            _this.getTableData()
          }
        })
      },
			//下架
			handleDown: function (index, row) {
        var _this = this
        _this.listLoading = true;
        _this.$ajax.post('/carousel/down/'+row.id).then(function(res){
          _this.listLoading = false;
          if(res.success){
            _this.$message.success('下架成功')
            _this.getTableData()
          }
        })
			},
			//显示新增界面
			handleAdd: function () {
				this.addForm = {
          name: '',
          path: '',
          imageUrl: ''
        }
        this.fileList = []
        this.imageUrlList = []
        this.addLoading = false
        this.addFormVisible = true
			},
      sortChanged: function(column){
        this.sort = column.prop!=null?column.prop.replace('Name',''):''
        this.order = (column.order === 'ascending')?'asc':'desc'
        this.getTableData()
      },
      handleSuccess: function(response, file, fileList){
        if(response.success){
          var _this = this
          _this.imageUrlList = []
          fileList.forEach(element => {
            if(element.response === undefined){
              _this.imageUrlList.push(element.url)
            }else{
              _this.imageUrlList.push(_this.service+element.response.data[0])
            }
          });
        }
      },
      handleRemove: function(file, fileList){
        var _this = this
        _this.imageUrlList = []
        fileList.forEach(element => {
          if(element.response === undefined){
            _this.imageUrlList.push(element.url)
          }else{
            _this.imageUrlList.push(_this.service+element.response.data[0])
          }
        });
      },
      addSubmit: function(){
        if(this.imageUrlList.length>0){
          this.addForm.imageUrl = this.imageUrlList[0]
        }
        var _this = this
        _this.$refs.addForm.validate((valid) => {
          if (valid) {
            _this.$confirm('确认提交吗？', '提示', {}).then(() => {
              _this.addLoading = true;
              let para = Object.assign({}, _this.addForm);
              _this.$ajax.post('/carousel/add',para).then((res) => {
                _this.addLoading = false
                if(res.success){
                  _this.$message({
                    message: '提交成功',
                    type: 'success'
                  });
                  _this.addFormVisible = false
                  _this.getTableData()
                }
              });
            });
          }
        });
      },
      editSubmit: function(){
        if(this.imageUrlList.length>0){
          this.editForm.imageUrl = this.imageUrlList[0]
        }
        var _this = this
        _this.$refs.editForm.validate((valid) => {
          if (valid) {
            _this.$confirm('确认提交吗？', '提示', {}).then(() => {
              _this.editLoading = true;
              let para = Object.assign({}, _this.editForm);
              _this.$ajax.post('/carousel/update',para).then((res) => {
                _this.editLoading = false
                if(res.success){
                  _this.$message({
                    message: '提交成功',
                    type: 'success'
                  });
                  _this.editFormVisible = false
                  _this.getTableData()
                }
              });
            });
          }
        });
      }
    },
    mounted(){
      this.service = this.$imageService
    },
    computed: {
      action: function(){
        return this.service + "/image/up"
      }
    }
	}

</script>

<style scoped lang="scss">
.img{
  cursor: pointer;
}
</style>
