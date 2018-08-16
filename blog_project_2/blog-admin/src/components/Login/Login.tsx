import {Button, Form, Icon, Input, message} from 'antd'
import {FormComponentProps} from 'antd/lib/form'
import * as React from 'react'
import './style.less'
import { regiseter }  from '../../services'

const FormItem = Form.Item

export interface IHistory {
    push: (pathname: string) => void
}

interface IProps extends FormComponentProps {
    userName: string
    password: string
    login: (payload: object) => void
    register: (payload: object) => void
    loading: boolean
    token?: string
    history: IPush
}

interface IPush {
    push: (pathname: string) => void
    history: IHistory
}

class Login extends React.Component<IProps, any> {
    // pass:string;
    // repss:string;

    public static getDerivedStateFromProps(props: IProps) {
        const {history, token} = props
        if (token) {
            history.push('/admin')
        }
        return null
    }

    public state = {
        isRegister:false
    }
    public handleSubmit = (e: any) => {
        e.preventDefault()
        const {form, login} = this.props
        form.validateFields((err, values) => {
            if (!err) {
                login(values)
            }
        })
    }
    public handleRegisterSubmit = (e: any) => {
        e.preventDefault()
        const {form} = this.props
        form.validateFields((err, values) => {
            if (!err) {
                if (values.password !== values.repassword) {
                    message.warning("两次密码不一致")
                } else {
                    regiseter(values);
                    this.setState({isRegister:false});
                }

            }
        })
    }

    public isLogin = () => {
        const user = localStorage.getItem('user')
        if (user && user !== 'undefined') {
            this.props.history.push('/admin')
        } else {
            // this.props.history.push('/admin/login')
        }
    }

    public componentDidMount() {
        this.isLogin()
    }

    public  registerView = () => {
        this.setState({isRegister:true})
    }
    public  loginView = () => {
        this.setState({isRegister:false})
    }

    public render() {
        const isRegister = this.state.isRegister
        const {loading} = this.props
        const {getFieldDecorator} = this.props.form
        return (
            <>
                {
                    isRegister === false ?

                <div className="login">
                    <div className="logo">
                        <img alt="logo" src="https://i.imgur.com/8Mc0rBJ.png"/>
                        <span>博客登录</span>
                    </div>
                    <Form onSubmit={this.handleSubmit}>
                        <FormItem hasFeedback={true}>
                            {getFieldDecorator('userName', {
                                rules: [{required: true, message: '账号不能为空!'}]
                            })(
                                <Input
                                    prefix={
                                        <Icon
                                            type="user"
                                            style={{color: 'rgba(0,0,0,.5)', fontSize: 16}}
                                        />
                                    }
                                    placeholder="请输入账号"
                                />
                            )}
                        </FormItem>
                        <FormItem hasFeedback={true}>
                            {getFieldDecorator('password', {
                                rules: [{required: true, message: '密码不能为空!'}]
                            })(
                                <Input
                                    prefix={
                                        <Icon
                                            type="lock"
                                            style={{color: 'rgba(0,0,0,.5)', fontSize: 16}}
                                        />
                                    }
                                    type="password"
                                    placeholder="请输入密码"
                                />
                            )}
                        </FormItem>
                        <FormItem>
                            <Button
                                loading={loading}
                                type="primary"
                                htmlType="submit"
                                className="login-form-button">
                                登录
                            </Button>
                        </FormItem>
                    </Form>
                    <a href="javascript:void(0)" onClick={this.registerView} className="aright">注册</a>
                </div>
                :

                <div className="register">
                    <div className="logo">
                        <img alt="logo" src="https://i.imgur.com/8Mc0rBJ.png"/>
                        <span>博客注册</span>
                    </div>
                    <Form onSubmit={this.handleRegisterSubmit}>
                        <FormItem hasFeedback={true}>
                            {getFieldDecorator('userName', {
                                rules: [{required: true, message: '账号不能为空!'}]
                            })(
                                <Input
                                    prefix={
                                        <Icon
                                            type="user"
                                            style={{color: 'rgba(0,0,0,.5)', fontSize: 16}}
                                        />
                                    }
                                    placeholder="请输入账号"
                                />
                            )}
                        </FormItem>
                        <FormItem hasFeedback={true}>
                            {getFieldDecorator('password', {
                                rules: [{required: true, message: '密码不能为空!'}]
                            })(
                                <Input
                                    prefix={
                                        <Icon
                                            type="lock"
                                            style={{color: 'rgba(0,0,0,.5)', fontSize: 16}}
                                        />
                                    }
                                    type="password"
                                    placeholder="请输入密码"
                                />
                            )}
                        </FormItem>
                        <FormItem hasFeedback={true}>
                            {getFieldDecorator('repassword', {
                                rules: [{required: true, message: '密码不能为空!'}]
                            })(
                                <Input
                                    prefix={
                                        <Icon
                                            type="lock"
                                            style={{color: 'rgba(0,0,0,.5)', fontSize: 16}}
                                        />
                                    }
                                    type="password"
                                    placeholder="请再次输入密码"
                                />
                            )}
                        </FormItem>
                        <FormItem>
                            <Button
                                loading={loading}
                                type="primary"
                                htmlType="submit"
                                className="login-form-button">
                                提交
                            </Button>
                        </FormItem>
                    </Form>
                    <a href="javascript:void(0)" onClick={this.loginView}  className="aright">登录</a>
                </div>
                }
            </>

        )
    }
}

export default Form.create()(Login)
