<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="queryForm">
				<el-form-item>
					<el-input v-model="queryForm.userName" placeholder="操作人姓名"></el-input>
				</el-form-item>
        <el-form-item>
          <el-select v-model="queryForm.module" placeholder="请选择模块">
            <el-option
              v-for="(value,key) in queryForm.modules"
              :key="key"
              :label="value"
              :value="key">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-select v-model="queryForm.operType" placeholder="请选择操作类型">
            <el-option
              v-for="(value,key) in queryForm.operTypes"
              :key="key"
              :label="value"
              :value="key">
            </el-option>
          </el-select>
        </el-form-item>
				<el-form-item>
					<el-button type="primary" v-on:click="getTableData">查询</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表-->
		<el-table :data="tableData" stripe highlight-current-row v-loading="listLoading" @sort-change="sortChanged" style="width: 100%;" :default-sort = "{prop: 'operateTime', order: 'descending'}" :empty-text="message">
			<el-table-column type="index" width="60" label="序号">
			</el-table-column>
			<el-table-column prop="userName" label="操作人" width="100" show-overflow-tooltip sortable='custom'>
			</el-table-column>
      <el-table-column prop="module" label="操作模块" width="120" show-overflow-tooltip sortable='custom'>
			</el-table-column>
      <el-table-column prop="operType" label="操作类型" width="120" show-overflow-tooltip sortable='custom'>
			</el-table-column>
      <el-table-column prop="title" label="操作简介" width="150" show-overflow-tooltip>
			</el-table-column>
			<el-table-column prop="operateTime" label="操作时间" width="250" sortable='custom'>
			</el-table-column>
			<el-table-column label="操作">
				<template slot-scope="scope">
					<el-button size="small" @click="handleDetail(scope.$index, scope.row)" v-hasPermission="'system.log.detail'">详情</el-button>
				</template>
			</el-table-column>
		</el-table>

		<!--工具条-->
		<el-col :span="24" class="toolbar">
			<el-pagination layout="total, prev, pager, next" @current-change="handleCurrentChange" :page-size="pageSize" :total="total" prev-text="上一页" next-text="下一页" background style="float:right;">
			</el-pagination>
		</el-col>

		<!--详情界面-->
		<el-dialog title="详情" :visible.sync="detailFormVisible" :close-on-click-modal="false">
			<el-form :model="detailForm" label-width="80px" label-position="left" ref="detailForm">
        <el-row>
          <el-col :span="12">
            <el-form-item label="操作人">
              <span>{{detailForm.userName}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="操作IP">
              <span>{{detailForm.clientIp}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="操作模块">
              <span>{{queryForm.modules[detailForm.module] || '未知模块'}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="操作类型">
              <span>{{queryForm.operTypes[detailForm.operType] || '未知类型'}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="操作简介">
              <span>{{detailForm.title}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="操作耗时">
              <span>{{detailForm.time}}ms</span>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="操作结果">
              <span>{{detailForm.result}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="操作参数">
              <div class="wrap">{{detailForm.params}}</div>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="操作内容">
              <div class="wrap">{{detailForm.content}}</div>
            </el-form-item>
          </el-col>
        </el-row>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="detailFormVisible = false">返回</el-button>
			</div>
		</el-dialog>
	</section>
</template>

<script>
	export default {
		data() {
			return {
				queryForm: {
          userName: '',
          module: '',
          operType: '',
          modules: {},
          operTypes: {}
				},
				tableData: [],
        total: 0,
        pages: 0,
        page: 1,
        pageSize: 10,
        sort: 'operateTime',
        order: 'desc',
        message: '',
				listLoading: false,

				detailFormVisible: false,//详情界面是否显示
				detailLoading: false,
				//详情界面数据
				detailForm: []

			}
		},
		methods: {
			handleCurrentChange(val) {
				this.page = val;
				this.getTableData();
			},
			//获取操作日志列表
			getTableData() {
        var _this = this;
				let para = {
          userName: _this.queryForm.userName,
          module: _this.queryForm.module,
          operType: _this.queryForm.operType,
          page: _this.page,
          pageSize: _this.pageSize,
          sort: _this.sort,
          order: _this.order
				};
        _this.listLoading = true;
				_this.$ajax.get('/system/log/operation/page',para).then((res) => {
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
			//详情
			handleDetail: function (index, row) {
        var _this = this;
				_this.$ajax.get('/system/log/get/'+row.id).then(function(res){
          if(res.success){
            _this.detailForm = res.data
            _this.detailFormVisible = true;
          }else{
            _this.$message.error(res.message);
          }
        })
			},
      sortChanged: function(column){
        this.sort = column.prop
        this.order = (column.order === 'ascending')?'asc':'desc'
        this.getTableData()
      }
    },
    created(){
      var _this = this;
      _this.$ajax.get('/system/log/module').then(function(res){
        if(res.success){
          _this.queryForm.modules = res.data
        }
      })
      _this.$ajax.get('/system/log/operType').then(function(res){
        if(res.success){
          _this.queryForm.operTypes = res.data
        }
      })
    }
	}

</script>

<style scoped lang="scss">
.wrap{
  word-wrap: break-word;
  word-break: normal;
}
</style>
