import * as React from 'react';
export const Comment = () => (
    <div style={{border:"solid 1px lightgray", marginTop:"10px", padding:"10px"}}>
        <h3 className="h3">{"张三："}</h3>
        <div style={{fontSize:"15px"}}>&nbsp;&nbsp;&nbsp;&nbsp;{"this is comment text"}</div>
    </div>
)