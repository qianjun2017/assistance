<template>
	<section>
    <div v-if="view =='list'">
      <!--工具条-->
      <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
        <el-form :inline="true" :model="queryForm">
          <el-form-item>
            <el-input v-model="queryForm.nickName" placeholder="客户昵称"></el-input>
          </el-form-item>
          <el-form-item>
            <el-select v-model="queryForm.status" placeholder="请选择账号状态" clearable>
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
          </el-form-item>
        </el-form>
      </el-col>

      <!--列表-->
      <el-table :data="tableData" stripe highlight-current-row v-loading="listLoading" @sort-change="sortChanged" style="width: 100%;" :default-sort = "{prop: 'createTime', order: 'descending'}" :empty-text="message">
        <el-table-column prop="nickName" label="客户昵称" width="250" show-overflow-tooltip>
          <template slot-scope="scope">
            <img :src = "scope.row.avatarUrl" width="40" height="40"/>
            <span style="margin-left: 10px">{{ scope.row.nickName }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="retailer" label="是否商家" width="100">
          <template slot-scope="scope">
            <span>{{ scope.row.retailer?'是':'否' }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="150" sortable='custom'>
          <template slot-scope="scope">
            <span>{{ statuss[scope.row.status] }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="注册日期" width="180" sortable='custom'>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button size="small" @click="handleDetail(scope.$index, scope.row)" v-hasPermission="'customer.detail'">详情</el-button>
            <el-button type="warning" size="small" @click="handleLock(scope.$index, scope.row)" v-if="scope.row.status === 'normal'" v-hasPermission="'customer.lock'">锁定</el-button>
            <el-button type="warning" size="small" @click="handleUnlock(scope.$index, scope.row)" v-if="scope.row.status === 'locked'" v-hasPermission="'customer.unlock'">解锁</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!--工具条-->
      <el-col :span="24" class="toolbar">
        <el-pagination layout="total, prev, pager, next" @current-change="handleCurrentChange" :page-size="pageSize" :total="total" prev-text="上一页" next-text="下一页" background style="float:right;">
        </el-pagination>
      </el-col>
    </div>

    <div v-if="view =='detail'">
      <div>
        <table>
          <tr>
            <td>
              <div class="user_avatar">
                <img v-lazy="customer.avatarUrl" class="img_avatar">
              </div>
            </td>
            <td>
              <div>
                <span class="display_name">{{customer.nickName}}</span>
                <span :title="statuss[customer.status]" class="tips"><i :class="status"></i></span>
              </div>
              <div>
                <ul class="user_profile">
                  <li>
                    <span class="text_gray">粉龄：<span :title="createTime" class="tips">{{time}}</span></span>
                  </li>
                  <li>
                    <span class="text_gray">商家：{{customer.retailer?'是':'否'}}</span>
                  </li>
                </ul>
              </div>
            </td>
          </tr>
        </table>
      </div>
      <div class="button-area">
        <el-button size="small" @click="back()">返回</el-button>
      </div>
    </div>

	</section>
</template>

<script>
	export default {
		data() {
			return {
				queryForm: {
          nickName: '',
          status: ''
        },
        tableData: [],
        statuss: {'normal': '正常', 'locked': '锁定'},
        total: 0,
        pages: 0,
        page: 1,
        pageSize: 8,
        sort: 'createTime',
        order: 'desc',
        message: '',
        listLoading: false,
        
        view: 'list',

        customer: {}
			}
		},
		methods: {
			handleCurrentChange(val) {
				this.page = val;
				this.getTableData();
			},
			//获取客户列表
			getTableData() {
				let para = {
          nickName: this.queryForm.nickName,
          status: this.queryForm.status,
          page: this.page,
          pageSize: this.pageSize,
          sort: this.sort,
          order: this.order
				};
        this.listLoading = true;
				this.$ajax.get('/customer/page',para).then((res) => {
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
			//锁定
			handleLock: function (index, row) {
				this.$confirm('确认锁定客户【'+row.nickName+'】吗?', '提示', {
					type: 'warning'
				}).then(() => {
					this.listLoading = true;
					this.$ajax.post('/customer/lock/'+row.id).then((res) => {
            this.listLoading = false;
            if(res.success){
              this.$message({
                message: '已锁定',
                type: 'success'
              });
              this.getTableData();
            }
					});
				}).catch(() => {

				});
			},
			//解锁
			handleUnlock: function (index, row) {
				this.$confirm('确认解锁客户【'+row.nickName+'】吗?', '提示', {
					type: 'warning'
				}).then(() => {
					this.listLoading = true;
					this.$ajax.post('/customer/unlock/'+row.id).then((res) => {
            this.listLoading = false;
            if(res.success){
              this.$message({
                message: '解锁成功',
                type: 'success'
              });
              this.getTableData();
            }
					});
				}).catch(() => {

				});
			},
      //详情
      handleDetail: function(index, row){
        this.$ajax.get('/customer/get/'+row.id).then((res)=>{
          if(res.success){
            this.customer = res.data
            this.view = 'detail'
          }else{
            this.$message.error(res.msg)
          }
        })
      },
      sortChanged: function(column){
        this.sort = column.prop
        this.order = (column.order === 'ascending')?'asc':'desc'
        this.getTableData()
      },
      back: function(){
        this.view = 'list'
      }
    },
    computed: {
      time: function(){
        var day = 24 * 60 * 60 *1000
        if(this.customer.createTime){
          var createTime = new Date()
          var dateArr = this.customer.createTime.split(" ")[0].split("-")
          createTime.setFullYear(dateArr[0], dateArr[1]-1, dateArr[2])
          var time = (new Date().getTime() - createTime.getTime())/day
          return time>=365?(Math.floor(time/365)+'年'):time>=30?(Math.floor(time/30)+'个月'):(Math.ceil(time)+'天')
        }
      },
      createTime: function(){
        return "注册时间："+this.customer.createTime
      },
      status: function(){
        if(this.customer.status==='normal'){
          return 'el-icon-success'
        }else{
          return 'el-icon-warning'
        }
      }
    }
	}

</script>

<style scoped lang="scss">
td{
  vertical-align: middle
}
li{
  list-style: none;
  padding-top: 10px;
}
tr, td, ul{
  margin: 0px;
  padding: 0px;
}
.tips{
  cursor: pointer;
}
td{
  vertical-align: top;
  .user_avatar{
    padding-right: 15px;
    .img_avatar{
      width: 180px;
      height: 180px;
      border: 1px solid #ccc;
      padding: 2px;
    }
  }
  .display_name{
    font-size: 20px;
    font-weight: bold;
    color: #454545;
  }
  .user_profile{
    margin-top: 10px;
    line-height: 0.8em;
    color: #333
  }
  .text_gray{
    color: Gray;
  }
}
.button-area{
  clear: both;
  width: 100%;
}
</style>
