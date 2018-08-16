import {all} from 'redux-saga/effects'
import {watchYieldArticles} from './articles'
import {watchYieldCollect} from './collect'
import {watchYieldInfo} from './info'
import {watchYieldSay} from './say'
import {watchYieldLogin} from './user'
import {watchYieldArticle} from './article'

export default function* rootSaga() {
    yield all([
        watchYieldArticles(),
        watchYieldCollect(),
        watchYieldInfo(),
        watchYieldLogin(),
        watchYieldSay(),
        watchYieldArticle()
    ])
}
