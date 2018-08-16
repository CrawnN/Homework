import {combineReducers} from 'redux'
import {articles, visible} from './articles'
import collect from './collect'
import info from './info'
import say from './say'
import user from './user'
import article from './article'

const reducers = combineReducers({
    article,
    articles,
    collect,
    info,
    say,
    user,
    visible
})
export default reducers
