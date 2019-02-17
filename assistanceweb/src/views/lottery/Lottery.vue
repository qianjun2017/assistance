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
                    <el-select v-model="queryForm.status" placeholder="请选择抽奖状态" clearable>
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
                </el-table-column>
                <el-table-column prop="no" label="抽奖期数" width="100">
                    <template slot-scope="scope">
                        <span>第{{scope.row.no}}期</span>
                    </template>
                </el-table-column>
                <el-table-column prop="status" label="状态" width="150" sortable='custom'>
                    <template slot-scope="scope">
                        <el-tag :type="scope.row.status=='normal'?'success':'danger'">{{ statuss[scope.row.status] }}</el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="createTime" label="创建日期" width="180" sortable='custom'>
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button size="small" @click="handleDetail(scope.$index, scope.row)" v-hasPermission="'lottery.detail'">详情</el-button>
                        <el-button type="warning" size="small" @click="handleOver(scope.$index, scope.row)" v-if="scope.row.status === 'normal'" v-hasPermission="'lottery.over'">结束</el-button>
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
            <div class="lottery-detail-box">
                <div class="lottery-detail-item lottery-detail-basic-box">
                    <div class="lottery-detail-basic-text">
                        <div class="lottery-detail-basic-text-item">
                            <span class="lottery-detail-basic-text-name">第{{lottery.no}}期</span>
                        </div>
                        <div class="lottery-detail-basic-text-item lottery-detail-prize-box" v-if="lottery.prizeList.length>0">
                            <template v-for="prize in lottery.prizeList">
                                <div class="lottery-detail-prize" :key="prize.id">
                                    <el-tag :type="prize.status=='normal'?'success':'danger'">
                                        <div class="lottery-detail-prize-item lottery-detail-prize-name">{{prize.name}}</div>
                                        <div class="lottery-detail-prize-item lottery-detail-prize-number">{{prize.total-prize.quantity}}({{prize.total}})</div>
                                        <div class="lottery-detail-prize-item lottery-detail-prize-number">{{prize.weight/100}}%</div>
                                    </el-tag>
                                </div>
                            </template>
                        </div>
                        <div class="lottery-detail-basic-text-item" v-if="lottery.prizeList.length>0">
                            <span class="lottery-detail-basic-text-invalid">*红色为结束奖项，示例：奖项名称 剩余数量(总数量) 中奖概率</span>
                        </div>
                    </div>
                </div>
                <div class="lotteryCustomers">
                    <el-col :span="24" class="titlebar">
                        <span class="title">中奖明细</span>
                        <el-button size="small" class="operation" @click="back">返回</el-button>
                    </el-col>

                    <!--列表-->
                    <el-table :data="lotteryPrize.tableData" stripe highlight-current-row v-loading="lotteryPrize.listLoading" style="width: 100%;" :empty-text="lotteryPrize.message">
                        <el-table-column type="index" width="60" label="序号">
                        </el-table-column>
                        <el-table-column prop="name" label="奖品名称" width="150" show-overflow-tooltip>
                        </el-table-column>
                        <el-table-column prop="nickName" label="中奖客户" width="200" show-overflow-tooltip>
                        </el-table-column>
                        <el-table-column prop="status" label="兑奖状态" width="150">
                            <template slot-scope="scope">
                                <el-tag :type="scope.row.status=='toBeExchange'?'success':'danger'">{{ lotteryPrize.statuss[scope.row.status] }}</el-tag>
                            </template>
                        </el-table-column>
                        <el-table-column prop="createTime" label="中奖时间">
                        </el-table-column>
                        <el-table-column prop="exchangeTime" label="兑奖时间">
                        </el-table-column>
                    </el-table>

                    <!--工具条-->
                    <el-col :span="24" class="titlebar">
                        <el-pagination layout="total, prev, pager, next" @current-change="handleLotteryPrizeCurrentChange" :page-size="lotteryPrize.pageSize" :total="lotteryPrize.total" :current-page="lotteryPrize.page" prev-text="上一页" next-text="下一页" background style="float:right;">
                        </el-pagination>
                    </el-col>
                </div>
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
            statuss: {'normal': '正常', 'over': '结束'},
            total: 0,
            pages: 0,
            page: 1,
            pageSize: 8,
            sort: 'createTime',
            order: 'desc',
            message: '',
            listLoading: false,
            
            view: 'list',

            lottery: {},

            lotteryPrize: {
                tableData: [],
                statuss: {'toBeExchange': '未兑奖', 'exchanged': '已兑奖', 'expired': '已过期'},
                total: 0,
                pages: 0,
                page: 1,
                pageSize: 4,
                sort: 'createTime',
                order: 'desc',
                message: '',
                listLoading: false,
            }
        }
    },
    methods: {
        handleCurrentChange(val) {
            this.page = val
            this.getTableData()
        },
        //获取抽奖列表
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
            this.$ajax.get('/lottery/page',para).then((res) => {
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
        //结束
        handleOver: function (index, row) {
            this.$confirm('确认结束客户【'+row.nickName+'】第'+row.no+'期抽奖吗?', '提示', {
                type: 'warning'
            }).then(() => {
                this.listLoading = true;
                this.$ajax.post('/lottery/over/'+row.id).then((res) => {
                    this.listLoading = false;
                    if(res.success){
                        this.$message({
                            message: '已结束',
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
            this.$ajax.get('/lottery/get/'+row.id).then((res)=>{
                if(res.success){
                    this.lottery = res.data
                    this.getLotteryPrizeTableData()
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
        },
        //获取中奖明细列表
        getLotteryPrizeTableData() {
            let para = {
                lotteryId: this.lottery.id,
                page: this.lotteryPrize.page,
                pageSize: this.lotteryPrize.pageSize,
                sort: this.lotteryPrize.sort,
                order: this.lotteryPrize.order
            };
            this.lotteryPrize.listLoading = true;
            this.$ajax.get('/lottery/customer/page',para).then((res) => {
                this.lotteryPrize.listLoading = false;
                if(res.success){
                    this.lotteryPrize.page = res.page;
                    this.lotteryPrize.pageSize = res.pageSize;
                    this.lotteryPrize.total = res.total;
                    this.lotteryPrize.pages = res.pages;
                    this.lotteryPrize.tableData = res.data;
                }else{
                    this.lotteryPrize.tableData = [];
                    this.lotteryPrize.page = 0;
                    this.lotteryPrize.total = 0;
                    this.lotteryPrize.pages = 0;
                    this.lotteryPrize.message = res.message;
                }
            });
        },
        handleLotteryPrizeCurrentChange(val) {
            this.lotteryPrize.page = val
            this.getLotteryPrizeTableData()
        }
    }
}
</script>

<style scoped lang="scss">
.lottery-detail-box{
    display: flex;
    flex-direction: column;
    .lottery-detail-item{
        width: 100%;
    }
    .lottery-detail-basic-box{
        display: flex;
        .lottery-detail-basic-image{
            width: 300px;
            flex-shrink: 0;
            img{
                width: 100%;
                max-width: 100%;
            }
        }
        .lottery-detail-basic-text{
            flex-grow: 1;
            padding-left: 18px;
            .lottery-detail-basic-text-item{
                width: 100%;
                font: 12px/1.5 tahoma,arial,'Hiragino Sans GB','\5b8b\4f53',sans-serif;
                line-height: 21px;
                min-height: 35px;
            }
            .lottery-detail-basic-text-name{
                font-size: 16px;
                font-weight: 700;
                color: #3C3C3C;
            }
            .lottery-detail-basic-text-description{
                margin-top: 10px;
                background: #eef1f6;
                padding: 15px 16px;
                line-height: 30px;
                font-family: "Source Sans Pro", "Helvetica Neue", Arial, sans-serif;
                display: block;
            }
            .lottery-detail-basic-text-invalid{
                color: #F40;
                font-size: 8px;
            }
        }
    }
    .lottery-detail-prize-box{
        display: flex;
        flex-wrap: wrap;
        margin-top: 10px;
        .lottery-detail-prize{
            padding: 10px 10px 10px 0;
            .lottery-detail-prize-item{
                display: inline-block;
            }
            .lottery-detail-prize-number{
                font-family: verdana,arial;
            }
        }
    }
    .lotteryCustomers{
        margin-top: 20px;
        .titlebar{
            background: #f2f2f2;
            padding: 10px;
            margin: 10px 0px;
            .title{
                color: #475669;
                font-weight: bold;
                line-height: 32px;
                display: inline-block;
            }
            .operation{
                float: right;
                margin-left: 0px;
            }
        }
    }
}
.lottery-detail-botton{
  width: 100%;
  margin-top: 5px;
}
</style>
