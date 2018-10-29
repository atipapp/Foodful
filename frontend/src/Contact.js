import React, { Component } from "react";

class Contact extends Component {

  constructor(props) {
    super(props);
    this.state = {
      error: null,
      isLoaded: false,
      health: "Down"
    };
  }

  componentDidMount() {
    fetch("http://staging.foodful.io/api/actuator/health")
      .then(res => res.json())
      .then(
        (result) => {
          this.setState({
            isLoaded: true,
            health: result.status
          });
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
        <h2>GOT QUESTIONS?</h2>
        <p>The easiest thing to do is send and email
          to the <a href="mailto:papp.attila971@gmail.com">developer</a>.
        </p>
        <h2>Api status: {this.state.health}</h2>
      </div>
    );
  }
}

export default Contact;