import React, { Component } from "react";
import {
    Route,
    NavLink,
    HashRouter
} from "react-router-dom";
import Dinners from "./Dinners";
import Profile from "./Profile";
import Contact from "./Contact";

class Main extends Component {
    render() {
        return (
            <HashRouter>
                <div>
                    <h1>Foodful</h1>
                    <h5>Thoughtful dinner planning</h5>
                    <ul className="header">
                        <li><NavLink exact to="/">Dinners</NavLink></li>
                        <li><NavLink to="/profile">My profile</NavLink></li>
                        <li><NavLink to="/contact">Contact</NavLink></li>
                    </ul>
                    <div className="content">
                        <Route exact path="/" component={Dinners} />
                        <Route path="/profile" component={Profile} />
                        <Route path="/contact" component={Contact} />
                    </div>
                </div>
            </HashRouter>
        );
    }
}

export default Main;
