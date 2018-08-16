import { Card } from 'antd'
import * as React from 'react'
import Highlight from 'react-highlight'
import { format } from '../../common'
import './highlight.less'
import './style.less'
import {CommetList} from '../Comment/CommentList'
import {CommentForm} from '../Comment/CommentForm'
import QueueAnim from "rc-queue-anim";
interface IArticle {
  _id: string
  title: string
  create_at: string
  access: string
  type: string
  tag: ITag
  content: string
}
interface ITag {
  color: string
  title: string
}
interface IParams {
  Id: string
}
interface IMatch {
  params: IParams
}
interface IProps {
  fetchArticle: (Id: string) => any
  article: IArticle
  match: IMatch
}
class Article extends React.Component<IProps> {
  public componentDidMount() {
    this.props.fetchArticle(this.props.match.params.Id)
  }
  public componentDidUpdate(prevProps: IProps) {
    if (this.props.match.params.Id !== prevProps.match.params.Id) {
      this.props.fetchArticle(this.props.match.params.Id)
    }
  }
  public addCode = (content: string) => {
    return content
      .replace(new RegExp('<pre>', 'g'), '<pre><code>')
      .replace(new RegExp('</pre>', 'g'), '</code></pre>')
  }
  public render() {
    const {
      title,
      create_at,
      access,
      type,
      content = '',
      tag = { title: '' }
    } = this.props.article
    return (
      <div className="articlebody">
        <div className={"widcenter"}>
            <QueueAnim
                animConfig={[
                    { opacity: [1, 0], translateY: [0, 50] },
                    { opacity: [1, 0], translateY: [0, -50] }
                ]}>
                <Card hoverable={true} bordered={false} >
                    <div>
                        <h3>{title}</h3>
                        <div className="tag">
                            <span>发表于：{format(create_at)}</span>
                            <span>分类：{type}</span>
                            <span>标签：{tag.title}</span>
                            <span>浏览：{access}</span>
                        </div>
                    </div>
                    <Highlight innerHTML={true} className="javascript">
                        {this.addCode(content)}
                    </Highlight>
                </Card>
            </QueueAnim>
            <QueueAnim
                animConfig={[
                    { opacity: [1, 0], translateY: [0, 50] },
                    { opacity: [1, 0], translateY: [0, -50] }
                ]}>
                <h3>评论区</h3>
                <Card>
                    <CommentForm />
                    <CommetList />
                </Card>
            </QueueAnim>
        </div>
      </div>
    )
  }
}

export default Article
