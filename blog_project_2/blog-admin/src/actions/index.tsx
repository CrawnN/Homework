// blog info const
import {IPayload} from "./articles";

export const REQUEST_INFO = 'REQUEST_INFO'
export type REQUEST_INFO = typeof REQUEST_INFO
export const RECEIVE_INFO = 'RECEIVE_INFO'
export type RECEIVE_INFO = typeof RECEIVE_INFO

// blog info action
export interface IRequestInfo {
  type: REQUEST_INFO
    payload: IPayload
}
export interface IReceiveInfo {
  type: RECEIVE_INFO
    payload: IPayload
  info: object
}
export type InfoAction = IRequestInfo | IReceiveInfo
