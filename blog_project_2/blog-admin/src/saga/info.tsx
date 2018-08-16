import { call, put, takeEvery } from 'redux-saga/effects'
import {InfoAction, RECEIVE_INFO, REQUEST_INFO} from '../actions'
import { fetchInfo } from '../services'

function* yieldInfo(action: InfoAction) {
  const {payload} = action
  const info = yield call(fetchInfo,payload)
  yield put({ type: RECEIVE_INFO, info })
}
export function* watchYieldInfo() {
  yield takeEvery(REQUEST_INFO, yieldInfo)
}
