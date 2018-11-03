import React, { Component } from "react";
import {
    Route,
    NavLink,
    BrowserRouter
} from "react-router-dom";
import Dinners from "./Dinners";
import Profile from "./Profile";
import Contact from "./Contact";
import Login from "./Login";

class Main extends Component {

    render() {
        return (
            <BrowserRouter>
                <div>
                <h1>Foodful</h1>
                    <h5>Thoughtful dinner planning</h5>
                    <ul className="header">
                        <li><NavLink exact to="/">Dinners</NavLink></li>
                        <li><NavLink to="/profile">My profile</NavLink></li>
                        <li><NavLink to="/contact">Contact</NavLink></li>
                    </ul>
                    <div className="content">
                        {
                            this.isLoggedIn() ? 
                                this.renderLoggedInContent() : this.renderLoggedOutContent()
                        }
                </div>

                </div>
            </BrowserRouter>
        );
    }

    isLoggedIn(){
        return false;
    }

    renderLoggedOutContent(){
        return(
            <div>
                <a 
                    href="https://www.facebook.com/v3.2/dialog/oauth?
                        client_id=268249693801528
                        &redirect_uri=http://localhost:3000/login"
                >
                    Facebook login
                </a>
                <Route path="/login" component={Login} />
            </div>
        );
    }

    renderLoggedInContent(){
        return(
            <div>
                <Route exact path="/" component={Dinners} />
                <Route path="/profile" component={Profile} />
                <Route path="/contact" component={Contact} />
                <Route path="/login" component={Login} />
            </div>
          
        );
    }
}

export default Main;
