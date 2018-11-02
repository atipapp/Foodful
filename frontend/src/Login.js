import React, { Component } from "react";

class Login extends Component {
    constructor(props){
        super(props);
        console.log(props);
        this.state = {
            facebookCode: props.location.search.replace("?code=", "")
        }
    }

    loginWithApi(){
        fetch('https://mywebsite.com/endpoint/', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                redirect_uri: 'http://localhost:3000/login',
                code: this.state.facebookCode,
                provider: 'FACEBOOK'
            })
        })
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