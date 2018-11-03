import React, { Component } from "react";

class Profile extends Component {
  render() {
    return (
      <div>
        <h2>David Hasselhoff</h2>
        <p>david@hasselhoff.com</p>
        <button type="button" class="btn btn-primary" onClick={this.handleLogout}>Logout</button>
      </div>
    );
  }

  handleLogout() {
    sessionStorage.clear();
    window.location.reload();
  }
}

export default Profile;