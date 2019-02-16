<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="queryForm">
				<el-form-item>
					<el-input v-model="filterText" placeholder="输入权限名称过滤"></el-input>
				</el-form-item>
        <el-form-item>
					<el-button @click="handleDetail" :disabled="this.checkedNodeData===null" v-hasPermission="'system.auth.detail'">详情</el-button>
				</el-form-item>
        <el-form-item>
					<el-button type="primary" @click="handleAdd" :disabled="this.checkedNodeData===null" v-hasPermission="'system.auth.add'">新增</el-button>
				</el-form-item>
        <el-form-item>
					<el-button type="warning" @click="handleEdit" :disabled="this.checkedNodeData===null" v-hasPermission="'system.auth.update'">修改</el-button>
				</el-form-item>
        <el-form-item>
					<el-button type="danger" @click="handleDel" :disabled="this.checkedNodeData===null" v-hasPermission="'system.auth.delete'">删除</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--权限树-->
    <el-col :span="24">
      <el-tree
        class="filter-tree"
        :data="treeData"
        :props="defaultProps"
        node-key="id"
        @node-click="checkNode"
        auto-expand-parent
        :filter-node-method="filterNode"
        :default-expanded-keys="[-1]"
        accordion
        ref="tree">
      </el-tree>
    </el-col>

		<!--编辑界面-->
		<el-dialog title="编辑" :visible.sync="editFormVisible" :close-on-click-modal="false">
			<el-form :model="editForm" label-width="80px" :rules="editFormRules" ref="editForm">
				<el-form-item label="权限名称" prop="authName">
					<el-input v-model="editForm.authName" auto-complete="off"></el-input>
				</el-form-item>
        <el-form-item label="权限编码" prop="authCode">
					<el-input v-model="editForm.authCode" auto-complete="off"></el-input>
				</el-form-item>
        <el-form-item label="权限类型" prop="authType">
          <el-radio-group v-model="editForm.authType">
            <el-radio label="menu">菜单权限</el-radio>
            <el-radio label="oper">功能权限</el-radio>
          </el-radio-group>
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
				<el-form-item label="权限名称" prop="authName">
					<el-input v-model="addForm.authName" auto-complete="off"></el-input>
				</el-form-item>
        <el-form-item label="权限编码" prop="authCode">
					<el-input v-model="addForm.authCode" auto-complete="off"></el-input>
				</el-form-item>
        <el-form-item label="权限类型" prop="authType">
          <el-radio-group v-model="addForm.authType">
            <el-radio label="menu">菜单权限</el-radio>
            <el-radio label="oper">功能权限</el-radio>
          </el-radio-group>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="addFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
			</div>
		</el-dialog>

    <!--详情界面-->
		<el-dialog title="详情" :visible.sync="detailFormVisible" :close-on-click-modal="false">
			<el-form :model="detailForm" label-width="80px" ref="detailForm">
				<el-form-item label="权限名称">
          <span>{{detailForm.authName}}</span>
				</el-form-item>
        <el-form-item label="权限编码">
          <span>{{detailForm.authCode}}</span>
				</el-form-item>
        <el-form-item label="权限类型">
          <span>{{detailForm.authTypeName}}</span>
				</el-form-item>
        <el-form-item label="权限层级">
          <span>{{detailForm.level}}</span>
				</el-form-item>
        <el-form-item label="创建时间">
          <span>{{detailForm.createTime}}</span>
				</el-form-item>
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
      filterText: '',
      queryForm: {
        authName: ""
      },
      treeData: [],
      checkedNodeData: null,
      defaultProps: {label: 'authName', children: 'subAuthList'},

      editFormVisible: false, //编辑界面是否显示
      editLoading: false,
      editFormRules: {
        authName: [
          { required: true, message: "请输入权限名称", trigger: "blur" }
        ],
        authCode: [
          { required: true, message: "请输入权限编码", trigger: "blur" }
        ],
        authType: [
          { required: true, message: "请输入权限类型", trigger: "blur" }
        ]
      },
      //编辑界面数据
      editForm: {
        id: 0,
        authName: '',
        authCode: '',
        authType: '',
        parentId: null
      },

      addFormVisible: false, //新增界面是否显示
      addLoading: false,
      addFormRules: {
        authName: [
          { required: true, message: "请输入权限名称", trigger: "blur" }
        ],
        authCode: [
          { required: true, message: "请输入权限编码", trigger: "blur" }
        ],
        authType: [
          { required: true, message: "请输入权限类型", trigger: "blur" }
        ]
      },
      //新增界面数据
      addForm: {
        authName: '',
        authCode: '',
        authType: '',
        parentId: null
      },

      detailFormVisible: false, //详情界面是否显示
      detailForm: {}
    };
  },
  methods: {
    filterNode(value, data) {
      if (!value) return true;
      if (data.id === -1) return true;
      return data.authName.indexOf(value) !== -1;
    },
    checkNode(data,node){
      this.checkedNodeData = data;
    },
    //获取权限树
    getTreeData() {
      var _this = this;
      _this.$ajax.get("/system/auth/tree").then(res => {
        _this.treeData = [];
        if (res.success) {
          _this.treeData.push(res.root);
        } else {
          _this.treeData.push({id: -1, authName: '权限树', subAuthList: []});
        }
      });
    },
    //删除
    handleDel: function() {
      var _this = this;
      if(_this.checkedNodeData==null){
        _this.$message.info("请选择要删除的权限节点")
        return
      }
      if(_this.checkedNodeData.id === -1){
        _this.$message.info("【权限树】节点不能删除")
        return
      }
      _this.$confirm("确认删除权限【"+_this.checkedNodeData.authName+"】吗？", "提示", {}).then(function(){
        _this.$ajax.post('/system/auth/delete/'+_this.checkedNodeData.id).then(function(res){
          if(res.success){
            _this.$message.success('删除成功')
            _this.checkedNodeData = null
            _this.getTreeData()
          }
        })
      })
    },
    //显示编辑界面
    handleEdit: function() {
      var _this = this;
      if(_this.checkedNodeData==null){
        _this.$message.info("请选择要编辑的权限节点")
        return
      }
      if(_this.checkedNodeData.id === -1){
        _this.$message.info("【权限树】节点不能编辑")
        return
      }
      _this.$ajax.get('/system/auth/get/'+_this.checkedNodeData.id).then(function(res){
        if(res.success){
          _this.editForm = res.data
          _this.checkedNodeData = null
          _this.editFormVisible = true
        }
      })
    },
    //显示新增界面
    handleAdd: function() {
      var _this = this;
      if(_this.checkedNodeData==null){
        _this.$message.info("请选择父权限节点")
        return
      }
      _this.addForm = {
        authName: '',
        authCode: '',
        authType: '',
        parentId: null
      }
      if(_this.checkedNodeData.id !== -1){
        _this.addForm.parentId = _this.checkedNodeData.id
      }
      _this.checkedNodeData = null
      _this.addFormVisible = true
    },
    //显示详情界面
    handleDetail:function(){
      var _this = this;
      if(_this.checkedNodeData==null){
        _this.$message.info("请选择权限节点")
        return
      }
      if(_this.checkedNodeData.id === -1){
        _this.$message.info("【权限树】节点无详情")
        return
      }
      _this.$ajax.get('/system/auth/get/'+_this.checkedNodeData.id).then(function(res){
        if(res.success){
          _this.detailForm = res.data
          _this.checkedNodeData = null
          _this.detailFormVisible = true
        }
      })
    },
    //编辑
    editSubmit: function() {
      var _this = this;
      _this.$refs.editForm.validate(valid => {
        if (valid) {
          _this.$confirm("确认提交吗？", "提示", {}).then(() => {
            _this.editLoading = true;
            let para = Object.assign({}, _this.editForm);
            _this.$ajax.post("/system/auth/update", para).then(res => {
              _this.editLoading = false;
              if (res.success) {
                _this.$message({
                  message: "提交成功",
                  type: "success"
                });
                _this.$refs["editForm"].resetFields();
                _this.editFormVisible = false;
                _this.getTreeData();
              }
            });
          });
        }
      });
    },
    //新增
    addSubmit: function() {
      var _this = this;
      _this.$refs.addForm.validate(valid => {
        if (valid) {
          _this.$confirm("确认提交吗？", "提示", {}).then(() => {
            _this.addLoading = true;
            let para = Object.assign({}, _this.addForm);
            _this.$ajax.post("/system/auth/add", para).then(res => {
              _this.addLoading = false;
              if (res.success) {
                _this.$message({
                  message: "提交成功",
                  type: "success"
                });
                _this.$refs["addForm"].resetFields();
                _this.addFormVisible = false;
                _this.getTreeData();
              }
            });
          });
        }
      });
    }
  },
  mounted(){
    this.getTreeData()
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  }
};
</script>

<style scoped lang="scss">

</style>
