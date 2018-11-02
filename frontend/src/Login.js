import React, { Component } from "react";

class Login extends Component {
    constructor(props){
        super(props);
        console.log(props);
        this.state = {
            facebookCode: props.location.search
        }
    }


    render() {
        return (
            <div>
                <text>Facebook login data: {this.state.facebookCode}</text>
            </div>

        );
    }
}

export default Login;