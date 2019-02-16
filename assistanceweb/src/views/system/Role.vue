<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="queryForm">
				<el-form-item>
					<el-input v-model="queryForm.roleName" placeholder="角色名称"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" v-on:click="getTableData">查询</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="handleAdd" v-hasPermission="'system.role.add'">新增</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表-->
		<el-table :data="tableData" stripe highlight-current-row v-loading="listLoading" @sort-change="sortChanged" @selection-change="selsChange" style="width: 100%;" :default-sort = "{prop: 'createTime', order: 'descending'}" :empty-text="message">
			<el-table-column type="selection" width="40">
			</el-table-column>
			<el-table-column type="index" width="60" label="序号">
			</el-table-column>
			<el-table-column prop="roleName" label="角色名称" width="240">
			</el-table-column>
			<el-table-column prop="createTime" label="创建时间" width="250" sortable='custom'>
			</el-table-column>
			<el-table-column label="操作">
				<template slot-scope="scope">
					<el-button size="small" @click="handleEdit(scope.$index, scope.row)" v-hasPermission="'system.role.edit'">编辑</el-button>
          <el-button type="warning" size="small" @click="authorize(scope.$index, scope.row)" v-hasPermission="'system.role.authorize'">授权</el-button>
					<el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)" v-hasPermission="'system.role.delete'">删除</el-button>
				</template>
			</el-table-column>
		</el-table>

		<!--工具条-->
		<el-col :span="24" class="toolbar">
			<el-button type="danger" @click="batchRemove" :disabled="this.sels.length===0" v-hasPermission="'system.role.delete'">批量删除</el-button>
			<el-pagination layout="total, prev, pager, next" @current-change="handleCurrentChange" :page-size="pageSize" :total="total" prev-text="上一页" next-text="下一页" background style="float:right;">
			</el-pagination>
		</el-col>

		<!--编辑界面-->
		<el-dialog title="编辑" :visible.sync="editFormVisible" :close-on-click-modal="false">
			<el-form :model="editForm" label-width="80px" :rules="editFormRules" ref="editForm">
				<el-form-item label="角色名称" prop="roleName">
					<el-input v-model="editForm.roleName" auto-complete="off"></el-input>
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
				<el-form-item label="角色名称" prop="roleName">
					<el-input v-model="addForm.roleName" auto-complete="off"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="addFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
			</div>
		</el-dialog>

    <!--授权界面-->
		<el-dialog title="分配权限" :visible.sync="authorizeFormVisible" :close-on-click-modal="false">
			<el-form :model="authorizeForm" label-width="80px" ref="authorizeForm">
				<el-form-item label="角色">
					<span>{{authorizeForm.roleName}}</span>
				</el-form-item>
        <el-form-item label="权限">
          <!--权限树-->
          <el-tree
            show-checkbox
            :data="authorizeForm.auths"
            :props="authorizeForm.defaultProps"
            node-key="id"
            auto-expand-parent
            :default-expanded-keys="[-1]"
            @check-change="checkChange"
            :default-checked-keys="authorizeForm.checkedAuths"
            accordion
            check-strictly
            ref="tree">
          </el-tree>
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
          roleName: ''
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
				sels: [],//列表选中列

				editFormVisible: false,//编辑界面是否显示
				editLoading: false,
				editFormRules: {
					roleName: [
						{ required: true, message: '请输入角色名称', trigger: 'blur' }
					]
				},
				//编辑界面数据
				editForm: {
          id: 0,
					roleName: ''
				},

				addFormVisible: false,//新增界面是否显示
				addLoading: false,
				addFormRules: {
					roleName: [
						{ required: true, message: '请输入角色名称', trigger: 'blur' }
          ]
				},
				//新增界面数据
				addForm: {
					roleName: ''
        },
        authorizeFormVisible: false,//授权界面是否显示
				authorizeLoading: false,
        authorizeForm: {
          roleId: 0,
          checkedAuths: [],
          roleName: '',
          auths:[],
          defaultProps: {label: 'authName', children: 'subAuthList'}
        }

			}
		},
		methods: {
			handleCurrentChange(val) {
				this.page = val;
				this.getTableData();
			},
			//获取角色列表
			getTableData() {
        var _this = this;
				let para = {
          roleName: _this.queryForm.roleName,
          page: _this.page,
          pageSize: _this.pageSize,
          sort: _this.sort,
          order: _this.order
				};
        _this.listLoading = true;
				_this.$ajax.get('/system/role/page',para).then((res) => {
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
					_this.$ajax.post('/system/role/delete/'+row.id).then((res) => {
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
					roleName: ''
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
							_this.$ajax.post('/system/role/update',para).then((res) => {
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
							_this.$ajax.post('/system/role/add',para).then((res) => {
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
			selsChange: function (sels) {
				this.sels = sels;
			},
			//批量删除
			batchRemove: function () {
        var _this = this;
				var ids = _this.sels.map(item => item.id).toString();
				_this.$confirm('确认删除选中记录吗？', '提示', {
					type: 'warning'
				}).then(() => {
					_this.listLoading = true;
					let para = { ids: ids };
					_this.$ajax.post('/system/role/delete/batch',para).then((res) => {
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
      sortChanged: function(column){
        this.sort = column.prop
        this.order = (column.order === 'ascending')?'asc':'desc'
        this.getTableData()
      },
      //显示授权界面
			authorize: function (index, row) {
				this.authorizeForm.roleId = row.id;
        this.authorizeForm.roleName = row.roleName;
        var _this = this;
        Promise.all([_this.$ajax.get('/system/role/'+row.id+'/auths/').then(function(res){
          if(res.success){
            _this.authorizeForm.checkedAuths = res.data;
            return Promise.resolve(0);
          }
          return Promise.reject(-1);
        }),
        _this.$ajax.get('/system/auth/tree').then(function(res){
          _this.authorizeForm.auths = [];
          if(res.success){
            _this.authorizeForm.auths.push(res.root)
            return Promise.resolve(0);
          }
          return Promise.reject(-2);
        })]).then(function(){
          _this.authorizeFormVisible = true;
        },function(res){
          if(res == -2){
            _this.$message.info('请先配置系统权限')
          }else{
            _this.$message.error('系统错误')
          }
        })
      },
      checkChange: function(){
        this.authorizeForm.checkedAuths = this.$refs.tree.getCheckedKeys()
      },
      authorizeSubmit: function(){
        let param = {};
        param.roleId = this.authorizeForm.roleId;
        param.authList = this.authorizeForm.checkedAuths;
        var _this = this;
        _this.$ajax.post('/system/role/authorize',param).then(function(res){
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

</style>
