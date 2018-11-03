import React, { Component } from "react";

class Login extends Component {
    constructor(props){
        super(props);
        console.log(props);
        this.state = {
            facebookCode: props.location.search.replace("?code=", "")
        }
        this.loginWithApi();
    }

    loginWithApi(){
        fetch('http://staging.foodful.io/api/auth-service/v1/oauth/login/social', {
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
        .then(res => res.json())
        .then(
          (result) => {
            console.log(result);
          },
          (error) => {
            this.setState({
              isLoaded: true,
              error
            });
          }
        )
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