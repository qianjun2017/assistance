<template>
  <section>
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="queryForm">
        <el-form-item>
				  <el-input v-model="queryForm.propertyDesc" placeholder="关键字"></el-input>
				</el-form-item>
        <el-form-item>
					<el-input v-model="queryForm.propertyName" placeholder="参数名称"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" v-on:click="getTableData">查询</el-button>
				</el-form-item>
        <el-form-item>
					<el-button type="primary" @click="handleAdd" v-hasPermission="'system.config.add'">新增</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表-->
		<el-table :data="tableData" stripe highlight-current-row v-loading="listLoading" style="width: 100%;" :empty-text="message">
			<el-table-column type="index" width="60" label="序号">
			</el-table-column>
      <el-table-column prop="propertyName" label="参数名称" width="250" show-overflow-tooltip>
			</el-table-column>
      <el-table-column prop="propertyValue" label="参数值" width="300" show-overflow-tooltip>
			</el-table-column>
      <el-table-column prop="propertyDesc" label="参数描述" show-overflow-tooltip>
			</el-table-column>
      <el-table-column label="操作">
				<template slot-scope="scope">
					<el-button size="small" @click="handleEdit(scope.$index, scope.row)" v-hasPermission="'system.config.edit'">编辑</el-button>
          <el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)" v-hasPermission="'system.config.delete'">删除</el-button>
				</template>
			</el-table-column>
		</el-table>

		<!--工具条-->
		<el-col :span="24" class="toolbar">
			<el-pagination layout="total, prev, pager, next" @current-change="handleCurrentChange" :page-size="pageSize" :total="total" prev-text="上一页" next-text="下一页" background style="float:right;">
			</el-pagination>
		</el-col>

    <!--编辑界面-->
		<el-dialog title="编辑" :visible.sync="editFormVisible" :close-on-click-modal="false">
			<el-form :model="editForm" label-width="80px" :rules="editFormRules" ref="editForm">
				<el-form-item label="参数名称" prop="propertyName">
					<el-input v-model="editForm.propertyName" auto-complete="off"></el-input>
				</el-form-item>
        <el-form-item label="参数值" prop="propertyValue">
					<el-input v-model="editForm.propertyValue" auto-complete="off"></el-input>
				</el-form-item>
        <el-form-item label="参数描述" prop="propertyDesc">
					<el-input v-model="editForm.propertyDesc" auto-complete="off"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="editFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
			</div>
		</el-dialog>

		<!--新增界面-->
		<el-dialog title="新增" :visible.sync="addFormVisible" :close-on-click-modal="false">
			<el-form :model="addForm" label-width="80px" :rules="addFormRules" ref="addForm">
				<el-form-item label="参数名称" prop="propertyName">
					<el-input v-model="addForm.propertyName" auto-complete="off"></el-input>
				</el-form-item>
        <el-form-item label="参数值" prop="propertyValue">
					<el-input v-model="addForm.propertyValue" auto-complete="off"></el-input>
				</el-form-item>
        <el-form-item label="参数描述" prop="propertyDesc">
					<el-input v-model="addForm.propertyDesc" auto-complete="off"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="addFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
			</div>
		</el-dialog>
  </section>
</template>

<script>
export default {
    data(){
      return {
        queryForm: {
          propertyDesc: '',
          propertyName: ''
        },
        tableData: [],
        total: 0,
        pages: 0,
        page: 1,
        pageSize: 8,
        message: '',
        listLoading: false,
				editFormVisible: false,//编辑界面是否显示
				editLoading: false,
				editFormRules: {
					propertyName: [
						{ required: true, message: '请输入参数名称', trigger: 'blur' }
          ],
          propertyValue: [
            { required: true, message: '请输入参数值', trigger: 'blur' }
          ]
				},
				//编辑界面数据
				editForm: {
          id: '',
					propertyName: '',
					propertyValue: '',
					propertyDesc: ''
				},
				addFormVisible: false,//新增界面是否显示
				addLoading: false,
				addFormRules: {
					propertyName: [
						{ required: true, message: '请输入参数名称', trigger: 'blur' }
          ],
          propertyValue: [
            { required: true, message: '请输入参数值', trigger: 'blur' }
          ]
				},
				//新增界面数据
				addForm: {
					propertyName: '',
					propertyValue: '',
					propertyDesc: ''
				}
      }
    },
    methods: {
      handleCurrentChange(val) {
				this.page = val;
				this.getTableData();
      },
      //系统参数
      getTableData() {
				let para = {
          propertyDesc: this.queryForm.propertyDesc,
          propertyName: this.queryForm.propertyName,
          page: this.page,
          pageSize: this.pageSize,
				};
        this.listLoading = true;
				this.$ajax.get('/system/config/page',para).then(res => {
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
      //新增界面
      handleAdd: function () {
        this.addForm = {
					propertyName: '',
					propertyValue: '',
					propertyDesc: ''
        };
				this.addFormVisible = true;
      },
      //编辑界面
      handleEdit: function (index, row) {
        this.editForm = Object.assign({}, row);
				this.editFormVisible = true;
      },
      //修改
      editSubmit: function () {
				this.$refs.editForm.validate((valid) => {
					if (valid) {
						this.$confirm('确认提交吗？', '提示', {}).then(() => {
							this.editLoading = true;
							let para = Object.assign({}, this.editForm);
							this.$ajax.post('/system/config/update',para).then((res) => {
                this.editLoading = false;
                if(res.success){
                  this.$message({
                    message: '提交成功',
                    type: 'success'
                  });
                  this.$refs['editForm'].resetFields();
                  this.editFormVisible = false;
                  this.getTableData();
                }
							});
						});
					}
				});
			},
			//新增
			addSubmit: function () {
				this.$refs.addForm.validate((valid) => {
					if (valid) {
						this.$confirm('确认提交吗？', '提示', {}).then(() => {
							this.addLoading = true;
							let para = Object.assign({}, this.addForm);
							this.$ajax.post('/system/config/add',para).then((res) => {
								this.addLoading = false;
								if(res.success){
                  this.$message({
                    message: '提交成功',
                    type: 'success'
                  });
                  this.$refs['addForm'].resetFields();
                  this.addFormVisible = false;
                  this.getTableData();
                }
							});
						});
					}
				});
      },
      handleDel: function (index, row) {
				this.$confirm('确认删除该记录吗?', '提示', {
					type: 'warning'
				}).then(() => {
					this.listLoading = true;
					this.$ajax.post('/system/config/delete/'+row.id).then((res) => {
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
			}
    },
    created(){
      this.getTableData()
    }
}
</script>

