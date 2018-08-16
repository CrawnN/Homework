import * as React from 'react'
import { Card, Pagination } from 'antd'
import { format } from '../../common'
import QueueAnim from "rc-queue-anim";
import { Link } from 'react-router-dom'
import './styles.less'
import './highlight.less'
import './style.less'
import {IPayload} from "../../actions/articles";

export interface IInfo {
    access: number
    accessData: object[]
    sayNumber: number
    articleNumber: number
    collectNumber: number
    lastArticle: IArticle
    lastCollect: object
    lastSay: object
    data: object[]
    articles: IArticle[];
    total: number;

}

interface IArticle {
    _id: string
    title: string
    create_at: string
    access: string
    type: string
    abstract: string
}
interface IProps {
    fetchInfo: (payload: IPayload) => void;
    total: number;
    articles: IArticle[];
    info: IInfo
}

class IndexPage extends React.Component<IProps> {
    public state = {
        pageIndex: 1,
        pageSize: 10
    }
    public onChange = (page: number, pageSize: number) => {
        this.setState(
            {
                pageIndex: page,
                pageSize
            },
            () => {
                this.props.fetchInfo(this.state)
            }
        )
    }

    public componentDidMount() {
        this.props.fetchInfo(this.state)
    }

    public render() {
        const pageIndex = this.state.pageIndex;
        const pageSize = this.state.pageSize;
        const { info } =  this.props;
        const { articles = [], total } = info
        return (
            <div className="index-page" >
               <div style={{width:1300,margin:'auto'}}>
                   <QueueAnim
                       animConfig={[
                           { opacity: [1, 0], translateY: [0, 50] },
                           { opacity: [1, 0], translateY: [0, -50] }
                       ]}>
                       {articles.map(item => (
                           <Card
                               key={item._id}
                               bordered={false}
                               hoverable={true}
                               className="article"
                               type="inner">
                               <div>
                                   <h1>{item.title}</h1>
                                   <p className="tag">
                                       <span>发表于：{format(item.create_at)}</span>
                                       <span>浏览：{item.access}</span>
                                   </p>
                                   <div className="abstract">{item.abstract}...</div>
                                   <Link to={`/article/${item._id}`}>
                                       <span className="link">阅读全文 >></span>
                                   </Link>
                               </div>
                           </Card>
                       ))}
                   </QueueAnim>
                   <QueueAnim className="Pagination" delay={1000}>
                       <Pagination
                           current={pageIndex}
                           pageSize={pageSize}
                           total={total}
                           onChange={this.onChange}
                           key="pagination"
                       />
                   </QueueAnim>
               </div>
            </div>
        )
    }
}

export default IndexPage
