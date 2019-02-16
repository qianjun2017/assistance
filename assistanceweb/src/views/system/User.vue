<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="queryForm">
				<el-form-item>
					<el-input v-model="queryForm.userName" placeholder="用户名"></el-input>
				</el-form-item>
        <el-form-item>
          <el-input v-model="queryForm.nickName" placeholder="用户昵称"></el-input>
				</el-form-item>
        <el-form-item>
          <el-input v-model="queryForm.phone" placeholder="手机号码"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" v-on:click="getTableData">查询</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="handleAdd" v-hasPermission="'system.user.add'">新增</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表-->
		<el-table :data="tableData" stripe highlight-current-row v-loading="listLoading" @sort-change="sortChanged" style="width: 100%;" :default-sort = "{prop: 'createTime', order: 'descending'}" :empty-text="message">
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="用户名">
              <span>{{ props.row.userName }}</span>
            </el-form-item>
            <el-form-item label="昵称">
              <span>{{ props.row.nickName }}</span>
            </el-form-item>
            <el-form-item label="姓名">
              <span>{{ props.row.realName }}</span>
            </el-form-item>
            <el-form-item label="手机号码">
              <span>{{ props.row.phone }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
			<el-table-column type="index" width="60" label="序号">
			</el-table-column>
			<el-table-column prop="nickName" label="昵称" width="100">
			</el-table-column>
			<el-table-column prop="realName" label="姓名" width="100">
			</el-table-column>
			<el-table-column prop="phone" label="手机号码" width="120">
			</el-table-column>
			<el-table-column prop="createTime" label="创建时间" width="180" sortable='custom'>
			</el-table-column>
			<el-table-column label="操作">
				<template slot-scope="scope">
					<el-button size="small" @click="handleEdit(scope.$index, scope.row)" v-hasPermission="'system.user.edit'">编辑</el-button>
          <el-button type="warning" size="small" @click="authorize(scope.$index, scope.row)" v-hasPermission="'system.user.authorize'">授权</el-button>
          <el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)" v-hasPermission="'system.user.delete'">删除</el-button>
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
				<el-form-item label="用户名" prop="userName">
					<el-input v-model="editForm.userName" auto-complete="off"></el-input>
				</el-form-item>
        <el-form-item label="密码" prop="password">
					<el-input type="password" v-model="editForm.password" auto-complete="off"></el-input>
				</el-form-item>
        <el-form-item label="昵称" prop="nickName">
					<el-input v-model="editForm.nickName" auto-complete="off"></el-input>
				</el-form-item>
        <el-form-item label="姓名" prop="realName">
					<el-input v-model="editForm.realName" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="手机号码" prop="phone">
					<el-input v-model="editForm.phone" auto-complete="off"></el-input>
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
				<el-form-item label="用户名" prop="userName">
					<el-input v-model="addForm.userName" auto-complete="off"></el-input>
				</el-form-item>
        <el-form-item label="密码" prop="password">
					<el-input type="password" v-model="addForm.password" auto-complete="off"></el-input>
				</el-form-item>
        <el-form-item label="昵称" prop="nickName">
					<el-input v-model="addForm.nickName" auto-complete="off"></el-input>
				</el-form-item>
        <el-form-item label="姓名" prop="realName">
					<el-input v-model="addForm.realName" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="手机号码" prop="phone">
					<el-input v-model="addForm.phone" auto-complete="off"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="addFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
			</div>
		</el-dialog>

    <!--授权界面-->
		<el-dialog title="分配角色" :visible.sync="authorizeFormVisible" :close-on-click-modal="false">
			<el-form :model="authorizeForm" label-width="80px" ref="authorizeForm">
				<el-form-item label="用户">
					<span>{{authorizeForm.userName}}</span>
				</el-form-item>
        <el-form-item label="角色">
          <el-checkbox :indeterminate="authorizeForm.isIndeterminate" v-model="authorizeForm.checkAll" @change="handleCheckAllChange">全选</el-checkbox>
          <el-checkbox-group v-model="authorizeForm.checkedRoles" @change="handleCheckedRolesChange">
            <el-checkbox v-for="role in authorizeForm.roles" :label="role.id" :key="role.id">{{role.roleName}}</el-checkbox>
          </el-checkbox-group>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="authorizeFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="authorizeSubmit" :loading="authorizeLoading">提交</el-button>
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
          nickName: '',
          realName: '',
          phone: ''
				},
				tableData: [],
        total: 0,
        pages: 0,
        page: 1,
        pageSize: 10,
        sort: 'createTime',
        order: 'desc',
        message: '',
				listLoading: false,
				editFormVisible: false,//编辑界面是否显示
				editLoading: false,
				editFormRules: {
					userName: [
						{ required: true, message: '请输入用户名', trigger: 'blur' }
          ]
				},
				//编辑界面数据
				editForm: {
          id: 0,
					userName: '',
					password: '',
					nickName: '',
					realName: '',
          phone: '',
          orgId: ''
				},
				addFormVisible: false,//新增界面是否显示
				addLoading: false,
				addFormRules: {
					userName: [
						{ required: true, message: '请输入用户名', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '请输入密码', trigger: 'blur' }
          ]
				},
				//新增界面数据
				addForm: {
					userName: '',
					password: '',
					nickName: '',
					realName: '',
          phone: '',
          orgId: ''
				},
        authorizeFormVisible: false,//授权界面是否显示
				authorizeLoading: false,
        authorizeForm: {
          userId: 0,
          checkedRoles: [],
          userName: '',
          isIndeterminate: true,
          roles:[],
          checkAll: false
        }
			}
		},
		methods: {
			handleCurrentChange(val) {
				this.page = val;
				this.getTableData();
			},
			//获取用户列表
			getTableData() {
        var _this = this;
				let para = {
          userName: _this.queryForm.userName,
          nickName: _this.queryForm.nickName,
          phone: _this.queryForm.phone,
          page: _this.page,
          pageSize: _this.pageSize,
          sort: _this.sort,
          order: _this.order
				};
        _this.listLoading = true;
				_this.$ajax.get('/system/user/page',para).then((res) => {
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
				_this.$confirm('确认删除该记录吗?', '提示', {
					type: 'warning'
				}).then(() => {
					_this.listLoading = true;
					_this.$ajax.post('/system/user/delete/'+row.id).then((res) => {
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
				this.editFormVisible = true;
				this.editForm = Object.assign({}, row);
			},
			//显示新增界面
			handleAdd: function () {
				this.addFormVisible = true;
				this.addForm = {
					userName: '',
					password: '',
					nickName: '',
					realName: '',
					phone: ''
        };
			},
			//编辑
			editSubmit: function () {
        var _this = this;
				_this.$refs.editForm.validate((valid) => {
					if (valid) {
						_this.$confirm('确认提交吗？', '提示', {}).then(() => {
							_this.editLoading = true;
							let para = Object.assign({}, _this.editForm);
							_this.$ajax.post('/system/user/update',para).then((res) => {
                _this.editLoading = false;
                if(res.success){
                  _this.$message({
                    message: '提交成功',
                    type: 'success'
                  });
                  _this.$refs['editForm'].resetFields();
                  _this.editFormVisible = false;
                  _this.getTableData();
                }
							});
						});
					}
				});
			},
			//新增
			addSubmit: function () {
        var _this = this;
				_this.$refs.addForm.validate((valid) => {
					if (valid) {
						_this.$confirm('确认提交吗？', '提示', {}).then(() => {
							_this.addLoading = true;
							let para = Object.assign({}, _this.addForm);
							_this.$ajax.post('/system/user/add',para).then((res) => {
								_this.addLoading = false;
								if(res.success){
                  _this.$message({
                    message: '提交成功',
                    type: 'success'
                  });
                  _this.$refs['addForm'].resetFields();
                  _this.addFormVisible = false;
                  _this.getTableData();
                }
							});
						});
					}
				});
			},
      sortChanged: function(column){
        this.sort = column.prop
        this.order = (column.order === 'ascending')?'asc':'desc'
        this.getTableData()
      },
      //显示授权界面
			authorize: function (index, row) {
				this.authorizeForm.userId = row.id;
        this.authorizeForm.userName = row.nickName;
        var _this = this;
        Promise.all([_this.$ajax.get('/system/user/'+row.id+'/roles/').then(function(res){
          if(res.success){
            _this.authorizeForm.checkedRoles = res.data;
            return Promise.resolve(0);
          }
          return Promise.reject(-1);
        }),
        _this.$ajax.get('/system/role/all').then(function(res){
          if(res.success){
            _this.authorizeForm.roles = res.data;
            return Promise.resolve(0);
          }
          return Promise.reject(-2);
        })]).then(function(){
          _this.authorizeForm.checkAll = _this.authorizeForm.roles.length === _this.authorizeForm.checkedRoles.length
          _this.authorizeForm.isIndeterminate = _this.authorizeForm.checkedRoles.length > 0 && _this.authorizeForm.checkedRoles.length < _this.authorizeForm.roles.length
          _this.authorizeFormVisible = true;
        },function(res){
          if(res == -2){
            _this.$message.info('请先配置系统角色')
          }else{
            _this.$message.error('系统错误')
          }
        })
      },
      handleCheckAllChange(val) {
        this.authorizeForm.checkedRoles = [];
        if(val){
          var _this = this;
          this.authorizeForm.roles.forEach(element => {
            _this.authorizeForm.checkedRoles.push(element.id)
          });
        }
        this.authorizeForm.isIndeterminate = false;
      },
      handleCheckedRolesChange(value) {
        let checkedCount = value.length;
        this.authorizeForm.checkAll = checkedCount === this.authorizeForm.roles.length;
        this.authorizeForm.isIndeterminate = checkedCount > 0 && checkedCount < this.authorizeForm.roles.length;
      },
      authorizeSubmit: function(){
        let param = {};
        param.userId = this.authorizeForm.userId;
        param.roleList = this.authorizeForm.checkedRoles;
        var _this = this;
        _this.$ajax.post('/system/user/authorize',param).then(function(res){
          if(res.success){
            _this.$message.success('保存成功')
            _this.$refs['authorizeForm'].resetFields();
            _this.authorizeFormVisible = false;
          }
        })
      }
		}
	}

</script>

<style scoped lang="scss">
  .demo-table-expand {
    font-size: 0;
  }
  .demo-table-expand label {
    width: 90px;
    color: #99a9bf;
  }
  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
  }
</style>
