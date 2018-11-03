import React, { Component } from "react";
import { Route, Redirect } from 'react-router-dom'
import Dinners from "./Dinners";
import Profile from "./Profile";
import Contact from "./Contact";

class Login extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loggedIn: false,
            facebookCode: props.location.search.replace("?code=", "")
        }
        this.loginWithApi();
    }

    loginWithApi() {
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
                    sessionStorage.setItem('access_token', result.access_token);
                    sessionStorage.setItem('access_token_expires', result.access_token_expires);
                    sessionStorage.setItem('refresh_token', result.refresh_token);
                    sessionStorage.setItem('refresh_token_expires', result.refresh_token_expires);

                    this.setState({
                        loggedIn: true,
                    })

                    window.location.reload();
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
                {this.state.loggedIn ? this.redirect() : this.renderWait()}
            </div>
        );
    }

    renderWait(){
        return(
            <div>
                Logging you in. Please wait.
            </div>
        )
    }

    redirect(){
        return(
            <div>
                Login successful. Please refresh.
                <Redirect push to="/" />
            </div>
        )
    }
}

export default Login;