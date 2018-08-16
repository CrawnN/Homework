import { connect } from 'react-redux'
import { REQUEST_INFO } from '../actions/'
import IndexPage from '../components/Index/IndexPage'
import { IInfo } from '../components/Index/IndexPage'
import {IPayload} from "../actions/articles";

interface IState {
  info: IInfo
}
const mapStateToProps = ({ info }: IState) => ({
  info
})
const mapDispatchToProps = (dispatch: any) => ({
  fetchInfo: (payload: IPayload) => {
    dispatch({
        payload,
        type: REQUEST_INFO })
  }
})
export default connect(
  mapStateToProps,
  mapDispatchToProps
)(IndexPage)
