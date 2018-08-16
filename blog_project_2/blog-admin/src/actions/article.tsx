import {
    RECEIVE_ARTICLE,
    REQUEST_ARTICLE,
} from '../constants'


// blog article action
export interface IRequestArticle {
    type: REQUEST_ARTICLE
    Id: string
}
export interface IReceiveArticle {
    type: RECEIVE_ARTICLE
    Id: string
    article: object[]
}
export type ArticleAction = IRequestArticle | IReceiveArticle
