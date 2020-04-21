<template>
  <div class="layout">
    <Layout>
      <Header :style="{position: 'fixed', width: '100%'}">
        <Menu mode="horizontal" theme="dark" active-name="用户管理" @on-select = 'menuValue'>
          <div class="topLeft">  <Button type="text" style="color: white" @click="logout">退出</Button>管理员&nbsp&nbsp<span style="color: yellow ">{{username}}</span></div>
          <div class="layout-nav">
            <MenuItem name="用户管理" style="font-size: large">
              <Icon type="ios-man" />
              用户管理
            </MenuItem>
            <MenuItem name="电影资源管理" style="font-size: large">
              <Icon type="ios-keypad"></Icon>
              电影资源管理
            </MenuItem>
            <MenuItem name="推荐设置管理" style="font-size: large;z-index: -100;position: relative">
              <Icon type="md-settings"></Icon>
                推荐设置管理
            </MenuItem>
          </div>
        </Menu>
      </Header>
      <!--
      adminUser
      -->
    <div v-if="user">
      <Content :style="{margin: '88px 20px 0', background: '#fff', minHeight: '500px',textAlign: 'center'}">
        <div align="center" style="margin: 15px">
          <Input  style="width: 300px;height: 22px" v-model="searchThing"  search enter-button placeholder="输入账号或用户名" clearable @on-search="searchUser" @on-change ="searchUser" />
        </div>
        <Table ref="selection"  :columns="columns1" :data="data6" border  stripe>
          <template slot-scope="{ row }" slot="name">
            <strong>{{ row.name }}</strong>
          </template>
          <template slot-scope="{ row, index }" slot="action">
            <Button type="primary" size="small" style="margin-right: 5px"  @click="aShow(index)">修改</Button>
            <Button type="error" size="small" @click="aRemove(index)">删除</Button>
          </template>
        </Table>
        <Modal
          v-model="modal1"
          title="修改用户信息"
        >
          <Form ref="formCustom" :model="formCustom" :rules="ruleCustom" :label-width="80">
            <FormItem label="账号">
              <Input type="text" disabled v-model="formCustom.id"/>
            </FormItem>
            <FormItem label="用户名" prop="name">
              <Input type="text" v-model="formCustom.name" placeholder="请输入昵称"/>
            </FormItem>
            <FormItem label="密码" prop="password">
              <Input type="password" password v-model="formCustom.password" placeholder="设置密码"/>
            </FormItem>
            <FormItem label="角色" prop="role">
              <Select filterable v-model="formCustom.role" placeholder="设置角色" style="width: 100px">
                <Option value="管理员">管理员</Option>
                <Option value="用户">用户</Option>
              </Select>
            </FormItem>
          </Form>
          <div slot="footer">
            <Button @click="aCancel" style="margin-left: 8px">取消</Button>
            <Button type="primary" @click="UpdateInfo">修改</Button>
          </div>
        </Modal>
      </Content>
      <Row type="flex" justify="end" v-if="a">
        <Page :total="total" :current="current" :page-size="pageSize" :page-size-opts="[10, 20]"
              show-elevator show-sizer show-total @on-change="aPageChange" @on-page-size-change="aPageSizeChange"/>
      </Row>
      <Row type="flex" justify="end" v-if="b">
        <Page :total="total" :current="current" :page-size="pageSize" :page-size-opts="[10, 20]"
              show-elevator show-sizer show-total @on-change="aPageChange1" @on-page-size-change="aPageSizeChange1"/>
      </Row>
    </div>
      <!--
      movies
      -->
      <div v-if="movie">
        <Content :style="{margin: '88px 20px 0', background: '#fff', minHeight: '500px',textAlign: 'center'}">
          <div class="add"><Button type="primary" @click="modal3 = true">添加</Button></div>
          <div align="center" style="margin: 15px">
            <Input  style="width: 300px;height: 22px" v-model="searchValue"  search enter-button placeholder="输入电影名" clearable @on-search="searchMovies" @on-change = "searchMovies" />
          </div>
          <Table ref="selection"  :columns="columns2" :data="data7" border  stripe>
            <template slot-scope="{ row }" slot="name">
              <strong>{{ row.name }}</strong>
            </template>
            <template slot-scope="{ row, index }" slot="action">
              <Button type="primary" size="small" style="margin-right: 5px"  @click="mShow(index)">修改</Button>
              <Button type="error" size="small" @click="mRemove(index)">删除</Button>
            </template>
          </Table>
          <Modal
            v-model="modal2"
            title="修改电影资源信息"
            @on-cancel="clear"
          >
            <Form ref="formMovies" :model="formMovies" :rules="ruleMovies" :label-width="80">
              <FormItem label="电影名" prop="name">
                <Input  type="text"  v-model="formMovies.name" placeholder="电影名"/>
              </FormItem>
              <FormItem label="主演"  prop="performer">
                <textarea style="width: 400px" type="text" v-model="formMovies.performer" placeholder="主演信息"></textarea>
              </FormItem>
              <FormItem label="海报图片">
                <Upload
                  ref="msgUpload"
                  :show-upload-list="true"
                  :on-success="msgSuccess"
                  :format="['jpg','jpeg','png','gif']"
                  :on-format-error="msgFormatError"
                  action="http://localhost:8080/upload" >
                  <div style="width: 58px;height:58px;line-height: 58px;margin-left: 20px">
                    <Icon type="ios-camera" size="20"></Icon>
                  </div>
                </Upload>
              </FormItem>
              <FormItem label="类型" prop="type">
                <textarea style="width: 400px" type="text"  v-model="formMovies.type" placeholder="电影类型"></textarea>
              </FormItem>
              <FormItem label="详细信息" prop="information">
                <textarea style="width: 400px" type="text" v-model="formMovies.information" placeholder="信息介绍"></textarea>
              </FormItem>
              <FormItem label="资源地址">
                <Upload
                  ref="movieUpload"
                  :show-upload-list="true"
                  :on-success="movieSuccess"
                  :format="['mp4']"
                  :on-format-error="movieFormatError"
                  action="http://localhost:8080/upload" >
                  <div style="margin-left: 20px">
                    <Icon type="ios-cloud-upload-outline" size="20"></Icon>
                  </div>
                </Upload>
              </FormItem>
            </Form>
            <div slot="footer">
              <Button @click="mCancel" style="margin-left: 8px">取消</Button>
              <Button type="primary" @click="UpdateMovie">修改</Button>
            </div>
          </Modal>

          <Modal
            v-model="modal3"
            title="添加电影资源信息"
            @on-cancel="addClear"
          >
            <Form ref="addForm" :model="addForm" :rules="ruleAdd" :label-width="90">
              <Row>
                <Col span="24" >
                  <FormItem label="电影名" prop="name">
                    <Input type="text"  v-model="addForm.name" placeholder="电影名"/>
                  </FormItem>
                </Col>
                <Col span="24">
                  <FormItem label="主演"  prop="performer">
                    <textarea style="width: 400px" type="text" v-model="addForm.performer" placeholder="主演信息"></textarea>
                  </FormItem>
                </Col>
                <Col span="24">
                  <FormItem label="海报图片">
                    <Upload
                      ref="aMsgUpload"
                      :show-upload-list="true"
                      :on-success="addMsgSuccess"
                      :format="['jpg','jpeg','png','gif']"
                      :on-format-error="msgFormatError"
                      action="http://localhost:8080/upload" >
                      <div style="width: 58px;height:58px;line-height: 58px;margin-left: 20px">
                        <Icon type="ios-camera" size="20"></Icon>
                      </div>
                    </Upload>
                  </FormItem>
                </Col>
                <Col span="24">
                  <FormItem label="类型" prop="type">
                    <textarea style="width: 400px" type="text"  v-model="addForm.type" placeholder="电影类型"></textarea>
                  </FormItem>
                </Col>
                <Col span="24">
                  <FormItem label="详细信息" prop="information">
                    <textarea style="width: 400px" type="text" v-model="addForm.information" placeholder="信息介绍"></textarea>
                  </FormItem>
                </Col>
                <Col span="24">
                  <FormItem label="资源地址">
                    <Upload
                      ref="aMovieUpload"
                      :show-upload-list="true"
                      :on-success="addMovieSuccess"
                      :format="['mp4']"
                      :on-format-error="movieFormatError"
                      action="http://localhost:8080/upload" >
                      <div style="margin-left: 20px">
                        <Icon type="ios-cloud-upload-outline" size="20"></Icon>
                      </div>
                    </Upload>
                  </FormItem>
                </Col>
              </Row>
            </Form>
            <div slot="footer">
              <Button @click="addCancel" style="margin-left: 8px">取消</Button>
              <Button type="primary" @click="addMovie">添加</Button>
            </div>
          </Modal>

        </Content>
        <Row type="flex" justify="end" v-if="c">
          <Page :total="total" :current="current" :page-size="pageSize" :page-size-opts="[10, 20]"
                show-elevator show-sizer show-total @on-change="mPageChange" @on-page-size-change="mPageSizeChange"/>
        </Row>
        <Row type="flex" justify="end" v-if="d">
          <Page :total="total" :current="current" :page-size="pageSize" :page-size-opts="[10, 20]"
                show-elevator show-sizer show-total @on-change="mPageChange1" @on-page-size-change="mPageSizeChange1"/>
        </Row>
      </div>
      <!--
      setting
      -->
      <div v-if="setting">
        <Content :style="{margin: '88px 20px 0', background: '#fff', minHeight: '500px',textAlign: 'center',fontSize: 'large'}">
         <div style="background-color: skyblue;width: 600px;height:500px;margin: 120px auto">
           <Divider orientation="center" style="font-size: large;padding: 20px">游客推荐设置</Divider>
           <Form ref="touristForm" :model="touristForm"  :label-width="280" class="font">
             <FormItem label="最低平均分推荐" prop="minScore">
               <InputNumber :max="10" :min="0" :step="1.2" v-model="touristForm.minScore"></InputNumber>
             </FormItem>
             <FormItem label="最高平均分推荐" prop="maxScore">
               <InputNumber :max="10" :min="0" :step="1.2" v-model="touristForm.maxScore"></InputNumber>
             </FormItem>
               <Button type="primary" @click="touristSubmit">确定</Button>
               <Button @click="tReset" style="margin-left: 8px">重置</Button>
           </Form>
           <br />
           <Divider orientation="center" style="font-size: large">用户推荐设置</Divider>
           <Form ref="userForm" :model="userForm" :label-width="280">
             <FormItem label="最低相似度推荐" prop="minScore">
               <InputNumber :max="10" :min="0" :step="1.2" v-model="userForm.minScore"></InputNumber>
             </FormItem>
             <FormItem label="最高相似度推荐" prop="maxScore">
               <InputNumber :max="10" :min="0" :step="1.2" v-model="userForm.maxScore"></InputNumber>
             </FormItem>
               <Button type="primary" @click="userSubmit">确定</Button>
               <Button @click="reset1" style="margin-left: 8px">重置</Button>
           </Form>
         </div>
        </Content>
      </div>
    </Layout>
  </div>
</template>
<script>
import { aList, aUpdate, aDel, aSearch} from '@/api/adminUser/adminUser'
import { mList, mUpdate, mAdd, mDel, mSearch} from '@/api/movie/movie'
import { tList, uList, tUpdate, userUpdate} from '@/api/setting/setting'
export default {
  data: function () {
    return {
      modal1: false,
      modal2: false,
      modal3: false,
      user: true,
      movie: false,
      setting: false,
      a: true,
      b: false,
      c: true,
      d: false,
      username: '',
      searchThing: '',
      searchValue: '',
      total: 0,
      current: 1,
      pageSize: 10,
      columns1: [
        {
          title: '账号',
          key: 'id'
        },
        {
          title: '用户名',
          key: 'name'
        },
        {
          title: '密码',
          key: 'password'
        },
        {
          title: '角色',
          key: 'role'
        },
        {
          title: '操作',
          slot: 'action',
          width: 150,
          align: 'center'
        }
      ],
      data6: [],
      columns2: [
        {
          title: '电影名',
          key: 'name'
        },
        {
          title: '主演',
          key: 'performer'
        },
        {
          title: '海报',
          key: 'msg'
        },
        {
          title: '类型',
          key: 'type'
        },
        {
          title: '详细信息',
          key: 'information'
        },
        {
          title: '资源地址',
          key: 'url'
        },
        {
          title: '操作',
          slot: 'action',
          width: 150,
          align: 'center'
        }
      ],
      data7: [],
      formCustom: {
        id: '',
        name: '',
        password: '',
        role: ''
      },
      ruleCustom: {
        name: [{
          required: true, message: '请输入昵称', trigger: 'blur'
        }],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'}
        ],
        role: [
          {required: true, message: '角色选择', trigger: 'blur'}
        ]
      },
      formMovies: {
        id: null,
        name: '',
        performer: '',
        msg: '',
        type: '',
        information: '',
        url: ''
      },
      ruleMovies: {
        name: [{
          required: true, message: '电影名', trigger: 'blur'
        }],
        performer: [{
          required: true, message: '主演', trigger: 'blur'
        }],
        type: [
          {required: true, message: '类型', trigger: 'blur'}
        ],
        information: [
          {required: true, message: '信息介绍', trigger: 'blur'}
        ]
      },
      addForm: {
        name: '',
        performer: '',
        msg: '',
        type: '',
        information: '',
        url: ''
      },
      ruleAdd: {
        name: [{
          required: true, message: '电影名', trigger: 'blur'
        }],
        performer: [{
          required: true, message: '主演信息', trigger: 'blur'
        }],
        type: [
          {required: true, message: '类型', trigger: 'blur'}
        ],
        information: [
          {required: true, message: '信息介绍', trigger: 'blur'}
        ]
      },
      touristForm: {
        id: null,
        minScore: 0,
        maxScore: 0
      },
      userForm: {
        id: null,
        minScore: 0,
        maxScore: 0
      }
    }
  },
  beforeMount () {
    this.usernameCheck()
    this.userList()
  },
  methods: {
    usernameCheck () {
      this.username = sessionStorage.getItem('username')
      if (this.username !== null && sessionStorage.getItem('role') === '管理员') {
        console.log()
      } else {
        this.$router.push('/')
      }
    },
    logout () {
      sessionStorage.removeItem('username')
      sessionStorage.removeItem('account')
      sessionStorage.removeItem('role')
      this.usernameCheck()
    },
    /** adminUser */
    userList () {
      this.a = true
      this.b = false
      aList(this.current - 1, this.pageSize).then(response => {
        if (response.data !== '') {
          let res = response.data
          this.total = res.total
          this.data6 = res.data
        }
      }).catch(error => {
        console.log(error)
      })
    },
    aShow (index) {
      this.modal1 = true
      this.formCustom.id = this.data6[index].id
      this.formCustom.name = this.data6[index].name
      this.formCustom.password = this.data6[index].password
      this.formCustom.role = this.data6[index].role
    },
    UpdateInfo () {
      this.$refs['formCustom'].validate((valid) => {
        if (valid) {
          aUpdate(this.formCustom).then(response => {
            if (response.data) {
              this.modal1 = false
              if (this.a) {
                this.userList()
              } else {
                this.current = 1
                this.searchList()
              }
              this.$Message.success('修改成功!')
            }
          })
        }
      })
    },
    aRemove (index) {
      this.$Modal.confirm({
        title: '删除',
        content: '确定要删除“' + this.data6[index].name + '”这条用户信息吗？',
        loading: true,
        onOk: () => {
          this.formCustom.id = this.data6[index].id
          this.formCustom.name = this.data6[index].name
          this.formCustom.password = this.data6[index].password
          this.formCustom.role = this.data6[index].role
          aDel(this.formCustom).then(res => {
            if (res.data) {
              this.$Modal.remove()
              this.$Message.success('删除成功')
              this.userList()
            } else {
              this.$Modal.remove()
              this.$Message.error('删除失败')
            }
          }).catch(error => {
            console.log(error)
          })
        }
      })
    },
    searchList () {
      this.a = false
      this.b = true
      aSearch(this.searchThing, this.current - 1, this.pageSize).then(response => {
        if (response.data !== '') {
          let res = response.data
          this.total = res.total
          this.data6 = res.data
        } else {
          this.total = 0
          this.$Message.error('暂无数据')
        }
      }).catch(error => {
        console.log(error)
      })
    },
    searchUser () {
      if (this.searchThing === '') {
        this.userList()
      } else {
        this.data6 = []
        this.a = false
        this.b = true
        this.current = 1
        this.searchList()
      }
    },
    aPageChange (page) {
      this.current = page
      this.userList()
    },
    aPageSizeChange (pageSize) {
      this.pageSize = pageSize
      this.userList()
    },
    aPageChange1 (page) {
      this.current = page
      this.searchList()
    },
    aPageSizeChange1 (pageSize) {
      this.pageSize = pageSize
      this.searchList()
    },
    aCancel () {
      this.modal1 = false
      this.$refs['upload'].clearFiles()
      this.handleReset('formCustom')
    },
    /** movies */
    moviesList () {
      this.c = true
      this.d = false
      mList(this.current - 1, this.pageSize).then(response => {
        if (response.data !== '') {
          let res = response.data
          this.total = res.total
          this.data7 = res.data
        }
      }).catch(error => {
        console.log(error)
      })
    },
    mShow (index) {
      this.modal2 = true
      this.formMovies.id = this.data7[index].id
      this.formMovies.name = this.data7[index].name
      this.formMovies.performer = this.data7[index].performer
      this.formMovies.type = this.data7[index].type
      this.formMovies.msg = this.data7[index].msg
      this.formMovies.information = this.data7[index].information
      this.formMovies.url = this.data7[index].url
    },
    UpdateMovie () {
      this.$refs['formMovies'].validate((valid) => {
        if (valid) {
          mUpdate(this.formMovies).then(response => {
            if (response.data) {
              this.modal2 = false
              if (this.c) {
                this.moviesList()
              } else {
                this.current = 1
                this.mSearchList()
              }
              this.$Message.success('修改成功!')
            }
          })
        }
      })
    },
    addMovie () {
      if (this.addForm.msg !== '') {
        if (this.addForm.url !== '') {
          this.$refs['addForm'].validate((valid) => {
            if (valid) {
              mAdd(this.addForm).then(response => {
                if (response.data) {
                  this.modal3 = false
                  this.addClear()
                  this.handleReset('addForm')
                  this.moviesList()
                  this.$Message.success('添加成功!')
                } else {
                  this.$Message.error('字段过长')
                }
              })
            }
          })
        } else {
          this.$Message.error('请上传资源')
        }
      } else {
        this.$Message.error('请上传图片')
      }
    },
    mRemove (index) {
      this.$Modal.confirm({
        title: '删除',
        content: '确定要删除《' + this.data7[index].name + '》这部电影信息吗？',
        loading: true,
        onOk: () => {
          this.formMovies.id = this.data7[index].id
          this.formMovies.name = this.data7[index].name
          this.formMovies.performer = this.data7[index].performer
          this.formMovies.type = this.data7[index].type
          this.formMovies.msg = this.data7[index].msg
          this.formMovies.information = this.data7[index].information
          this.formMovies.url = this.data7[index].url
          mDel(this.formMovies).then(res => {
            if (res.data) {
              this.$Modal.remove()
              this.$Message.success('删除成功')
              this.moviesList()
            } else {
              this.$Modal.remove()
              this.$Message.error('删除失败')
            }
          }).catch(error => {
            console.log(error)
          })
        }
      })
    },
    mSearchList () {
      this.c = false
      this.d = true
      mSearch(this.searchValue, this.current - 1, this.pageSize).then(response => {
        if (response.data !== '') {
          let res = response.data
          this.total = res.total
          this.data7 = res.data
        } else {
          this.total = 0
          this.$Message.error('暂无数据')
        }
      }).catch(error => {
        console.log(error)
      })
    },
    searchMovies () {
      if (this.searchValue === '') {
        this.moviesList()
      } else {
        this.data7 = []
        this.c = false
        this.d = true
        this.current = 1
        this.mSearchList()
      }
    },
    mPageChange (page) {
      this.current = page
      this.moviesList()
    },
    mPageSizeChange (pageSize) {
      this.pageSize = pageSize
      this.moviesList()
    },
    mPageChange1 (page) {
      this.current = page
      this.mSearchList()
    },
    mPageSizeChange1 (pageSize) {
      this.pageSize = pageSize
      this.mSearchList()
    },
    mCancel () {
      this.clear()
      this.modal2 = false
    },
    addCancel () {
      this.addClear()
      this.handleReset('addForm')
      this.modal3 = false
    },
    clear () {
      this.$refs['msgUpload'].clearFiles()
      this.$refs['movieUpload'].clearFiles()
      this.formMovies.name = ''
      this.formMovies.performer = ''
      this.formMovies.type = ''
      this.formMovies.msg = ''
      this.formMovies.information = ''
      this.formMovies.url = ''
    },
    addClear () {
      this.$refs['aMsgUpload'].clearFiles()
      this.$refs['aMovieUpload'].clearFiles()
      this.addForm.name = ''
      this.addForm.performer = ''
      this.addForm.type = ''
      this.addForm.msg = ''
      this.addForm.information = ''
      this.addForm.url = ''
    },
    /** setting */
    touristList () {
      tList().then(response => {
        if (response.data !== '') {
          let res = response.data
          this.touristForm.id = res.id
          this.touristForm.minScore = res.minScore
          this.touristForm.maxScore = res.maxScore
        }
      }).catch(error => {
        console.log(error)
      })
    },
    userSettingList () {
      uList().then(response => {
        if (response.data !== '') {
          let res = response.data
          this.userForm.id = res.id
          this.userForm.minScore = res.minScore
          this.userForm.maxScore = res.maxScore
        }
      }).catch(error => {
        console.log(error)
      })
    },
    touristSubmit () {
      if (this.touristForm.minScore <= 10 && this.touristForm.minScore >= 0 &&
        this.touristForm.maxScore <= 10 && this.touristForm.maxScore >= 0 &&
        this.touristForm.minScore <= this.touristForm.maxScore) {
        this.$Modal.confirm({
          title: '设置',
          content: '游客设置范围为' + this.touristForm.minScore + '--' + this.touristForm.maxScore + '?',
          loading: true,
          onOk: () => {
            tUpdate(this.touristForm).then(response => {
              if (response) {
                this.$Modal.remove()
                this.$Message.success('设置成功')
                this.touristList()
              } else {
                this.$Modal.remove()
                this.$Message.error('设置失败')
              }
            }).catch(error => {
              console.log(error)
            })
          }
        })
      } else {
        this.$Message.error('设置失败，数值范围0-10，且最小值要小于或等于最大值')
      }
    },
    userSubmit () {
      if (this.userForm.minScore <= 20 && this.userForm.minScore >= 0 &&
        this.userForm.maxScore <= 20 && this.userForm.maxScore >= 0 &&
        this.userForm.minScore <= this.userForm.maxScore) {
        this.$Modal.confirm({
          title: '设置',
          content: '用户设置范围为' + this.userForm.minScore + ' — ' + this.userForm.maxScore + '?',
          loading: true,
          onOk: () => {
            userUpdate(this.userForm).then(response => {
              if (response) {
                this.$Modal.remove()
                this.$Message.success('设置成功')
                this.userSettingList()
              } else {
                this.$Modal.remove()
                this.$Message.error('设置失败')
              }
            }).catch(error => {
              console.log(error)
            })
          }
        })
      } else {
        this.$Message.error('设置失败，数值范围0-20，且最小值要小于或等于最大值')
      }
    },
    tReset () {
      this.touristList()
    },
    reset1 () {
      this.userSettingList()
    },
    /** common */
    menuValue (name) {
      if (name === '用户管理') {
        this.user = true
        this.movie = false
        this.setting = false
        this.current = 1
        this.userList()
      }
      if (name === '电影资源管理') {
        this.user = false
        this.setting = false
        this.movie = true
        this.current = 1
        this.moviesList()
      }
      if (name === '推荐设置管理') {
        this.user = false
        this.setting = true
        this.movie = false
        this.touristList()
        this.userSettingList()
      }
    },
    handleReset (name) {
      this.$refs[name].resetFields()
    },
    /** 上传 */
    msgSuccess (res) {
      this.formMovies.msg = res
    },
    movieSuccess (res) {
      this.formMovies.url = res
    },
    addMsgSuccess (res) {
      this.addForm.msg = res
    },
    addMovieSuccess (res) {
      this.addForm.url = res
    },
    msgFormatError (file) {
      this.$Notice.warning({
        title: '图片格式错误',
        desc: '文件 ' + file.name + ' 不是jpg,jpeg,png，gif格式'
      })
    },
    movieFormatError: function (file) {
      this.$Notice.warning({
        title: '视频格式错误',
        desc: '文件 ' + file.name + ' 不是mp4格式'
      })
    }
  }
}
</script>
<style scoped>
  .layout{
    border: 1px solid #d7dde4;
    background: #f5f7f9;
    position: relative;
    border-radius: 4px;
    overflow: hidden;
    text-align: left;
  }
  .topLeft{
    width: 200px;
    height: 30px;
    float: left;
    text-align: center;
    line-height: 30px;
    color: chartreuse;
    position: relative;
    font-size: large;
    top: 15px;
    left: 20px;
  }
  .layout-nav{
    width: 550px;
    margin: 0 20px 0 auto;
  }
  .add{
    position: absolute;
    left: 20px;
    top: 96px;
  }
</style>
