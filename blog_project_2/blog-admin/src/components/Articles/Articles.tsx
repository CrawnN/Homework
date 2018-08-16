import { Button, message, Modal, Table, Tag, Tooltip } from 'antd'
import { ColumnProps } from 'antd/lib/table'
import * as React from 'react'
import { IPayload } from '../../actions/articles'
import EditArticle from './EditArticle'
import SearchForm from './SearchForm'
import './style.less'

const confirm = Modal.confirm
interface ITag {
  title: string
  color: string
}
export interface IArticle {
  _id: string
  content: string
  title: string
  tag: string | ITag
  create_at: string
  access: string
  abstract: string
  type: string
}
interface IHistory {
  push: (pathname: string) => void
}
interface IProps {
  articles: IArticle[]
  history: IHistory
  total: number
  payload: IPayload
  visible: boolean
  fetchArticle: (payload: object) => void
  deleteArticle: (id: string) => void
  toggleVisible: (visible: boolean) => void
  editArticle: (payload: object) => void
}
class Articles extends React.Component<IProps> {
  public state = {
    article: {}
  }
  public onChange = (pageIndex: number, pageSize: number) => {
    this.props.fetchArticle({ pageIndex, pageSize })
  }
  public onShowSizeChange = (pageIndex: number, pageSize: number) => {
    this.props.fetchArticle({ pageIndex, pageSize })
  }
  public componentDidMount() {
    this.props.fetchArticle({ pageIndex: 1, pageSize: 5 })
  }
  public deleteArticle = (id: string) => {
    confirm({
      cancelText: '取消',
      okText: '确定',
      onOk: () => {
        this.props.deleteArticle(id)
      },
      title: '确定删除这篇文章吗？',
      onCancel() {
        message.warning('取消删除')
      }
    })
  }
  public editArticle = (article: IArticle) => {
    this.props.toggleVisible(true)
    this.setState({ article })
  }
  public render() {
    const {
      articles,
      total,
      payload,
      toggleVisible,
      visible,
      editArticle,
      fetchArticle
    } = this.props
    const { pageIndex, pageSize } = payload
    const columns: Array<ColumnProps<IArticle>> = [
      {
        key: 'nature',
        render: text => <h4>{text.nature}</h4>,
        title: '文章类型'
      },
      {
        key: 'title',
        render: text => <h4>{text.title}</h4>,
        title: '文章标题'
      },
      {
        key: 'abstract',
        render: _ => (
          <Tooltip title={_.abstract}>
            <p className="abstract">{_.abstract}</p>
          </Tooltip>
        ),
        title: '文章简介'
      },
      {
        key: 'tag',
        render: _ => {
          if (typeof _.tag === 'string') {
            return <Tag key={_.tag}>{_.tag}</Tag>
          } else {
            return (
              <Tag key={_.tag.title} color={_.tag.color}>
                {_.tag.title}
              </Tag>
            )
          }
        },
        title: '标签'
      },
      {
        dataIndex: 'type',
        key: 'type',
        title: '分类'
      },
      {
        key: 'create_at',
        render: _ => <span>{new Date(_.create_at).toLocaleDateString()}</span>,
        title: '发表时间'
      },
      {
        align: 'center',
        dataIndex: 'access',
        key: 'access',
        title: '浏览数量'
      },
      {
        key: 'action',
        render: _ => (
          <span>
            <Button
              icon="edit"
              type="primary"
              size="small"
              style={{ marginRight: 10 }}
              onClick={() => this.editArticle(_)}>
              编辑
            </Button>
            <Button
              icon="delete"
              type="danger"
              size="small"
              onClick={() => this.deleteArticle(_._id)}>
              删除
            </Button>
          </span>
        ),
        title: '操作'
      }
    ]
    return (
      <div>
        <EditArticle
          {...this.state.article}
          toggleVisible={toggleVisible}
          visible={visible}
          editArticle={editArticle}
        />
        <div className="search-form">
          <SearchForm fetchArticle={fetchArticle} payload={payload} />
        </div>
        <Table
          scroll={{ x: 1000 }}
          columns={columns}
          bordered={true}
          dataSource={articles}
          rowKey="_id"
          pagination={{
            current: pageIndex,
            onChange: this.onChange,
            onShowSizeChange: this.onShowSizeChange,
            pageSize,
            showSizeChanger: true,
            total
          }}
        />
      </div>
    )
  }
}
export default Articles
