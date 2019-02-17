<template>
	<section>

    <!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="queryForm">
        <el-form-item>
          <el-select v-model="queryForm.code" placeholder="请选择调用接口" clearable>
            <el-option
              v-for="(value,key) in queryForm.codes"
              :key="key"
              :label="value"
              :value="key">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-date-picker
            v-model="queryForm.createTimeStart"
            type="datetime"
            placeholder="选择调用开始日期时间"
            value-format="yyyy-MM-dd HH:mm:ss"
            default-time="00:00:00">
          </el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-date-picker
            v-model="queryForm.createTimeEnd"
            type="datetime"
            placeholder="选择调用结束日期时间"
            value-format="yyyy-MM-dd HH:mm:ss"
            default-time="23:59:59">
          </el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getTableData">查询</el-button>
        </el-form-item>
			</el-form>
		</el-col>

		<!--列表-->
		<el-table :data="tableData" stripe highlight-current-row v-loading="listLoading" @sort-change="sortChanged" style="width: 100%;" :default-sort = "{prop: 'createTime', order: 'descending'}" :empty-text="message">
			<el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item>
              <div class="text-box"><label>请求报文</label><span class="text">{{ props.row.request }}</span></div>
            </el-form-item>
            <el-form-item>
              <div class="text-box"><label>应答报文</label><span class="text">{{ props.row.response }}</span></div>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column prop="code" label="接口代码">
			</el-table-column>
      <el-table-column prop="name" label="接口名称">
			</el-table-column>
      <el-table-column prop="type" label="接口类型">
			</el-table-column>
      <el-table-column prop="createTime" label="调用时间" sortable='custom'>
			</el-table-column>
		</el-table>

		<!--工具条-->
		<el-col :span="24" class="toolbar">
			<el-pagination layout="total, prev, pager, next" @current-change="handleCurrentChange" :page-size="pageSize" :total="total" prev-text="上一页" next-text="下一页" background style="float:right;">
			</el-pagination>
		</el-col>

	</section>
</template>

<script>
	export default {
		data() {
			return {
        queryForm: {
          code: '',
          createTimeStart: '',
          createTimeEnd: '',
          codes: {}
				},
				tableData: [],
        total: 0,
        pages: 0,
        page: 1,
        pageSize: 10,
        sort: 'createTime',
        order: 'desc',
        message: '',
				listLoading: false
			}
		},
		methods: {
			handleCurrentChange(val) {
				this.page = val;
				this.getTableData();
			},
			//获取接口调用列表
			getTableData() {
				let para = {
          page: this.page,
          pageSize: this.pageSize,
          sort: this.sort,
          order: this.order,
          code: this.queryForm.code,
          createTimeStart: this.queryForm.createTimeStart,
          createTimeEnd: this.queryForm.createTimeEnd
				};
        this.listLoading = true;
				this.$ajax.get('/interface/page',para).then((res) => {
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
      sortChanged: function(column){
        this.sort = column.prop
        this.order = (column.order === 'ascending')?'asc':'desc'
        this.getTableData()
      }
    },
    created(){
      this.$ajax.get('/interface/code').then((res)=>{
        if(res.success){
          this.queryForm.codes = res.data
        }
      })
    }
	}

</script>

<style scoped lang="scss">
  .demo-table-expand {
    font-size: 0;
  }
  .demo-table-expand label {
    width: 90px;
  }
  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 100%;
  }
  .demo-table-expand .el-form-item .text-box {
    display: inline-flex;
    .text{
      display: inline-block;
      word-break: break-all;
      width: 100%;
    }
  }
</style>
