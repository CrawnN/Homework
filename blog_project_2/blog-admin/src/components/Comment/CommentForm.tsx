import * as React from 'react';
import { Button, Form, Icon, Input } from 'antd'

const FormItem = Form.Item

export const CommentForm = () => (

    <div className="form-group">
        <form>
            <FormItem hasFeedback={true} >
                <Input
                    prefix={
                        <Icon
                            type="user"
                            style={{ color: 'rgba(0,0,0,.5)', fontSize: 16 }}
                        />
                    }
                    style={{height:80 }}
                    placeholder="我要评论..."
                />
            </FormItem>
            <FormItem style={{textAlign:'right' }}>
                <Button
                    type="primary"
                    htmlType="submit"
                    className="login-form-button"
                    >
                    提交
                </Button>
            </FormItem>
        </form>
    </div>
)