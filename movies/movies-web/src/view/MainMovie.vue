<template>
 <div>
   <div style="height: 50px" class="top">
     <Row type="flex" justify="space-between" class="code-row-bg">
       <Col span="8"  > <img src="../assets/1.gif"></Col>
       <Col  span="3"></Col>
       <Col span="6"><Input v-model="searchValue"  search enter-button placeholder="输入电影名或主演名" style="width: 280px" clearable @on-search="search" @on-change = "search" /></Col>
       <Col  span="4"></Col>
       <Col span="1">
        <Button type="primary" @click="modal1 = true" v-if="col3">登录</Button>
         <span :style="{fontSize: 'large'}" v-if="col4" style="color: chartreuse" >{{formCustom.username}}</span>
       </Col>
         <Modal class="topRight"
                v-model="modal1"
                title="登录"
                @on-cancel="handleReset('formLogin')"
         >
           <Form ref="formLogin" :model="formLogin" :rules="ruleLogin" :label-width="80">
             <FormItem label="账号" prop="account">
               <Input type="text" v-model="formLogin.account" placeholder="请输入账号"/>
             </FormItem>
             <FormItem label="密码" prop="passwd">
               <Input type="password" password v-model="formLogin.passwd" placeholder="请输入密码"/>
             </FormItem>
           </Form>
           <div slot="footer">
             <Button type="primary" @click="LoginSubmit">登录</Button>
             <Button @click="handleReset('formLogin')" style="margin-left: 8px">重置</Button>
           </div>
         </Modal>
       <Col span="1">
         <Button type="success" @click="modal2 = true" v-if="col3">注册</Button>
         <Button type="error" v-if="col4" @click = "logout">退出</Button></Col>
         <Modal
           v-model="modal2"
           title="注册"
           @on-cancel="handleReset('formCustom')"
          >
           <Form ref="formCustom" :model="formCustom" :rules="ruleCustom" :label-width="80">
             <FormItem label="账号" prop="account">
               <Input type="text" v-model="formCustom.account" placeholder="请输入账号"/>
             </FormItem>
             <FormItem label="用户名" prop="username">
               <Input type="text" v-model="formCustom.username" placeholder="请输入昵称"/>
             </FormItem>
             <FormItem label="密码" prop="passwd">
               <Input type="password" password v-model="formCustom.passwd" placeholder="设置密码"/>
             </FormItem>
             <FormItem label="确认密码" prop="passwdCheck">
               <Input type="password" password v-model="formCustom.passwdCheck" placeholder="请确认密码"/>
             </FormItem>
           </Form>
           <div slot="footer">
             <Button type="primary" @click="RegisterSubmit">注册</Button>
             <Button @click="handleReset('formCustom')" style="margin-left: 8px">重置</Button>
           </div>
         </Modal>
       <Col span="1"></Col>
     </Row>
   </div>

   <div class="layout">
   <Sider :style="{position: 'fixed', height: '100vh', left: 0, overflow: 'auto'}" width="10px">
     <RadioGroup class="leftSet" v-model="button4" type="button" size="large" @on-change="handleType">
       <Radio label="全部" style="font-size: large"></Radio><br/>
       <Radio label="喜剧" style="font-size: large"></Radio><br/>
       <Radio label="动作" style="font-size: large"></Radio><br/>
       <Radio label="爱情" style="font-size: large"></Radio><br/>
       <Radio label="动画" style="font-size: large"></Radio><br/>
       <Radio label="犯罪" style="font-size: large"></Radio><br/>
       <Radio label="武侠" style="font-size: large"></Radio><br/>
       <Radio label="恐怖" style="font-size: large"></Radio><br/>
       <Radio label="高分推荐" style="font-size: medium"></Radio><br/>
       <Radio v-if="col4" label="猜你喜欢" style="font-size: medium"></Radio><br/>
     </RadioGroup>
   </Sider>
   <Layout :style="{marginLeft: '120px'}">
     <Content :style="{padding: '0 10px 20px',backgroundColor: '#c5c8ce'}">
         <div :style="{height: '800px',textAlign: 'left'}" >
           <List item-layout="vertical" border>
             <ListItem v-for="item in data" :key="item.name" style="padding: 0px">
               <Col span="8">
                <div style="margin: 20px 20px;height: auto">
                  <ListItemMeta :title="item.name" :description="item.performer" />
                  <div style="position: relative">
                    <div style="position: absolute;z-index: 1;color: darkorange;">
                      <span v-if="0!==item.avgScore" style="background-color: darkred;font-size: large">{{item.avgScore}}</span>
                      <span v-if="0===item.avgScore" >暂无评分</span>
                    </div>
                    <Poptip trigger="hover" :title="item.name" :content="item.information" word-wrap width="280" placement="bottom">
                      <img :src="item.msg" style="width: 380px;height: 300px">
                    </Poptip>
                  </div>
                 <div style="font-size: large">
                   <Tooltip content="播放">
                     <a :href="item.url" v-on:click="wacth(item.id)" target="_blank"> <Icon type="md-arrow-dropright-circle" /></a>
                   </Tooltip>
                   <Tooltip content="下载">
                     <Button type="text" shape="circle" @click="downloadMovie(item.url,item.id)"><Icon type="md-arrow-down" /> </Button>
                   </Tooltip>
                   <Rate v-model="value" allow-half  :clearable="false"  />{{value * 2}}
                   <button @click="doScore(item.id,item.name)">评分</button>
                 </div>
                </div>
               </Col>
             </ListItem>
           </List>
           <div style="font-size: large;margin: 400px 500px" v-if="nothing">
             还不了解您的喜好，去看看其他资源吧
           </div>
          <div style="position: relative;margin: 400px;font-size: large" v-if="spinShow">
           <Spin size="large" fix >
             <Icon type="ios-loading" size=24 class="demo-spin-icon-load"></Icon>
             <div>加载中</div>
           </Spin>
         </div>
         </div>
         <div  v-if="a">
           <Page :total="total" :current="current" :page-size="pageSize" :page-size-opts="[9, 18]"
                 show-elevator show-sizer show-total @on-change="pageChange" @on-page-size-change="pageSizeChange"/>
         </div>
       <div  v-if="b">
         <Page :total="total" :current="current" :page-size="pageSize" :page-size-opts="[9, 18]"
               show-elevator show-sizer show-total @on-change="pageChange1" @on-page-size-change="pageSizeChange1"/>
       </div>
       <div  v-if="c">
         <Page :total="total" :current="current" :page-size="pageSize" :page-size-opts="[9, 18]"
               show-elevator show-sizer show-total @on-change="pageChange2" @on-page-size-change="pageSizeChange2"/>
       </div>
       <div  v-if="d">
         <Page :total="total" :current="current" :page-size="pageSize" :page-size-opts="[9, 18]"
               show-elevator show-sizer show-total @on-change="pageChange3" @on-page-size-change="pageSizeChange3"/>
       </div>
       <div  v-if="f">
         <Page :total="total" :current="current" :page-size="pageSize" :page-size-opts="[9, 18]"
               show-elevator show-sizer show-total @on-change="pageChange4" @on-page-size-change="pageSizeChange4"/>
       </div>
     </Content>
   </Layout>
 </div>
</div>
</template>
<script>
import { loginCheck, loginSave } from '@/api/adminUser/adminUser'
import { show, searchNameOrPerformer, typeShow, highScoreShow, likeShow } from '@/api/movie/movie'
import { relationSave } from '@/api/else/relation'
export default {
  data () {
    const validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'))
      } else {
        if (this.formCustom.passwdCheck !== '') {
          // 对第二个密码框单独验证
          this.$refs.formCustom.validateField('passwdCheck')
        }
        callback()
      }
    }
    const validatePassCheck = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请确认密码'))
      } else if (value !== this.formCustom.passwd) {
        callback(new Error('两次密码不一致'))
      } else {
        callback()
      }
    }
    return {
      modal1: false,
      modal2: false,
      col3: true,
      col4: false,
      value: 1,
      a: true,
      b: false,
      c: false,
      d: false,
      f: false,
      spinShow: false,
      nothing: false,
      total: 0,
      current: 1,
      pageSize: 9,
      button4: '全部',
      searchValue: '',
      formLogin: {
        account: '',
        passwd: ''
      },
      formCustom: {
        account: '',
        username: '',
        passwd: '',
        passwdCheck: ''
      },
      ruleLogin: {
        account: [
          { required: true, message: '请输入账号', trigger: 'blur' }
        ],
        passwd: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ]
      },
      ruleCustom: {
        account: [{
          required: true, message: '请输入账号', trigger: 'blur'
        }],
        username: [{
          required: true, message: '请输入昵称', trigger: 'blur'
        }],
        passwd: [
          {required: true, validator: validatePass, trigger: 'blur'}
        ],
        passwdCheck: [
          {required: true, validator: validatePassCheck, trigger: 'blur'}
        ]
      },
      data: [],
      relationForm: {
        auId: '',
        moId: null,
        watch: null,
        download: null,
        score: null
      }
    }
  },
  beforeMount () {
    this.usernameCheck()
    this.pageShow()
  },
  methods: {
    /** 登录 */
    LoginSubmit () {
      this.$refs['formLogin'].validate((valid) => {
        if (valid) {
          loginCheck(this.formLogin.account, this.formLogin.passwd).then(res => {
            if (res.data === '') {
              this.$Message.error('该账户不存在或密码错误!')
            } else {
              if (res.data.role === '管理员') {
                sessionStorage.setItem('username', res.data.name)
                sessionStorage.setItem('role', res.data.role)
                this.$router.push('/Administrators')
                this.handleReset('formLogin')
                this.$Message.success('管理员您好!')
              } else {
                this.formCustom.username = res.data.name
                this.modal1 = false
                this.col3 = false
                this.col4 = true
                sessionStorage.setItem('account', res.data.id)
                sessionStorage.setItem('username', res.data.name)
                sessionStorage.setItem('role', res.data.role)
                this.$Message.success('登录成功!')
              }
            }
          })
            .catch(error => {
              console.log(error)
            })
        } else {
          this.$Message.error('登陆失败!')
        }
      })
    },
    usernameCheck () {
      this.formCustom.username = sessionStorage.getItem('username')
      if (this.formCustom.username !== null && sessionStorage.getItem('role') === '用户') {
        this.col3 = false
        this.col4 = true
      } else {
        sessionStorage.removeItem('username')
        sessionStorage.removeItem('account')
        sessionStorage.removeItem('role')
        this.formCustom.username = ''
        this.col3 = true
        this.col4 = false
      }
    },
    logout () {
      sessionStorage.removeItem('username')
      sessionStorage.removeItem('account')
      sessionStorage.removeItem('role')
      this.usernameCheck()
      this.handleReset('formLogin')
      this.button4 = '全部'
      this.handleType()
    },
    /** 注册 */
    RegisterSubmit () {
      this.$refs['formCustom'].validate((valid) => {
        if (valid) {
          loginSave(this.formCustom).then(response => {
            if (response.data) {
              this.handleReset('formCustom')
              this.modal2 = false
              this.$Message.success('注册成功，请登录!')
            } else {
              this.$Message.error('该账号已存在!')
            }
          })
            .catch(error => {
              console.log(error)
            })
        } else {
          this.$Message.error('注册失败!')
        }
      })
    },
    handleReset (name) {
      this.formCustom.username = ''
      this.$refs[name].resetFields()
    },
    /** 搜索 */
    searchMovieList () {
      searchNameOrPerformer(this.searchValue, this.current - 1, this.pageSize).then(response => {
        let res = response.data
        if (res.data.length !== 0) {
          let res = response.data
          this.total = res.total
          this.data = res.data
        } else {
          this.total = 0
          this.$Message.error('暂无数据')
        }
      })
    },
    search () {
      if (this.searchValue === '') {
        this.button4 = '全部'
        this.handleType()
      } else {
        this.data = []
        this.a = false
        this.b = true
        this.c = false
        this.d = false
        this.f = false
        this.current = 1
        this.searchMovieList()
      }
    },
    relationPost () {
      relationSave(this.relationForm).then(response => {
        if (response.data !== '') {
          this.$Message.info(response.data)
        }
      })
    },
    wacth (id) {
      if (this.formCustom.username !== '') {
        this.relationForm.watch = 1
        this.relationForm.moId = id
        this.relationForm.auId = sessionStorage.getItem('account')
        this.relationPost()
      }
    },
    /** 下载 */
    downloadMovie (url, id) {
      if (this.formCustom.username !== '' && this.formCustom.username !== null) {
        this.relationForm.download = 2
        this.relationForm.moId = id
        this.relationForm.auId = sessionStorage.getItem('account')
        this.relationPost()
        window.location.href = 'http://localhost:8080/download?url=' + url
      } else {
        this.$Message.error('请登录')
      }
    },
    /** 评分 */
    doScore (id, name) {
      if (this.formCustom.username !== '' && this.formCustom.username !== null) {
        this.$Modal.confirm({
          title: '评分',
          content: '您对《' + name + '》的评分为 ' + 2 * this.value + ' 分?',
          loading: true,
          onOk: () => {
            this.relationForm.score = 2 * this.value
            this.relationForm.moId = id
            this.relationForm.auId = sessionStorage.getItem('account')
            this.relationPost()
            this.$Modal.remove()
            location.reload()
          }
        })
      } else {
        this.$Message.error('请登录')
      }
    },
    /** 展示 */
    pageShow () {
      show(this.current - 1, this.pageSize).then(response => {
        let res = response.data
        if (res.data.length !== 0) {
          this.total = res.total
          this.data = res.data
        }
      })
    },
    typeList () {
      typeShow(this.button4, this.current - 1, this.pageSize).then(response => {
        let res = response.data
        if (res.data.length !== 0) {
          this.total = res.total
          this.data = res.data
        }
      })
    },
    highScoreList () {
      this.spinShow = true
      highScoreShow(this.current - 1, this.pageSize).then(response => {
        let res = response.data
        if (res.data.length !== 0) {
          this.total = res.total
          this.data = res.data
          this.spinShow = false
        }
      })
    },
    likeList () {
      this.spinShow = true
      likeShow(sessionStorage.getItem('account'), this.current - 1, this.pageSize).then(response => {
        let res = response.data
        if (res.data.length !== 0) {
          this.total = res.total
          this.data = res.data
          this.spinShow = false
        } else {
          this.nothing = true
          this.spinShow = false
        }
      })
    },
    handleType () {
      this.nothing = false
      if (this.button4 === '全部') {
        this.a = true
        this.b = false
        this.c = false
        this.d = false
        this.f = false
        this.current = 1
        this.pageShow()
      } else if (this.button4 === '高分推荐') {
        this.a = false
        this.b = false
        this.c = false
        this.d = true
        this.f = false
        this.current = 1
        this.data = []
        this.highScoreList()
      } else if (this.button4 === '猜你喜欢') {
        this.a = false
        this.b = false
        this.c = false
        this.d = false
        this.f = true
        this.current = 1
        this.data = []
        this.likeList()
      } else {
        this.a = false
        this.b = false
        this.c = true
        this.d = false
        this.f = false
        this.current = 1
        this.data = []
        this.typeList()
      }
    },
    pageChange (page) {
      this.current = page
      this.pageShow()
    },
    pageSizeChange (pageSize) {
      this.pageSize = pageSize
      this.pageShow()
    },
    pageChange1 (page) {
      this.current = page
      this.searchMovieList()
    },
    pageSizeChange1 (pageSize) {
      this.pageSize = pageSize
      this.searchMovieList()
    },
    pageChange2 (page) {
      this.current = page
      this.typeList()
    },
    pageSizeChange2 (pageSize) {
      this.pageSize = pageSize
      this.typeList()
    },
    pageChange3 (page) {
      this.data = []
      this.current = page
      this.highScoreList()
    },
    pageSizeChange3 (pageSize) {
      this.data = []
      this.pageSize = pageSize
      this.highScoreList()
    },
    pageChange4 (page) {
      this.data = []
      this.current = page
      this.likeList()
    },
    pageSizeChange4 (pageSize) {
      this.data = []
      this.pageSize = pageSize
      this.likeList()
    }
  }
}
</script>
<style scoped>
  .layout{
    border: 1px solid #d7dde4;
    background: #515a6e;
    position: relative;
    border-radius: 4px;
    overflow: auto;
  }
  .top{
    padding: 10px;
  }
.leftSet{
  font-size: 46px;
  padding: 10px;
  margin-left: 0px;
}
 .top img {
    width: 200px;
    position: relative;
    margin-top: -12px;
   left: -50px;
  }
</style>
