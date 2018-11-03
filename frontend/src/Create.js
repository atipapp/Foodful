import React, { Component } from "react";
import { Formik, Form, Field, ErrorMessage } from 'formik';

class Create extends Component {
    render() {
        return (
            <div>
                <Formik
                    initialValues={{ title: '', location: '', date: '', time: '11:00' }}
                    onSubmit={(values, { setSubmitting }) => {
                        setTimeout(() => {
                            this.createDinner(this.makeApiDtoFromValues(values));
                            alert(JSON.stringify(this.makeApiDtoFromValues(values), null, 2));
                            setSubmitting(false);
                        }, 400);
                    }}
                >
                    {({ isSubmitting }) => (
                        <Form className="form-group">
                            <label htmlFor="title">Title</label>
                            <Field className="form-control" type="text" name="title" /> <br />
                            <label htmlFor="location">Location</label>
                            <Field className="form-control" type="text" name="location" /> <br />
                            <label htmlFor="date">Date</label>
                            <Field className="form-control" type="date" name="date" /> <br />
                            <label htmlFor="time">Time</label>
                            <Field className="form-control" type="time" name="time" /> <br />
                            <button className="btn btn-primary" type="submit" disabled={isSubmitting}>
                                Submit
                            </button>
                        </Form>
                    )}
                </Formik>
            </div>
        );
    }

    createDinner(dinner) {
        fetch('http://staging.foodful.io/api/dinner-service/v1/dinner', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(dinner)
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


    makeApiDtoFromValues(values) {
        return {
            title: values.title,
            location: values.location,
            scheduledTime: values.date + "T" + values.time + "+01:00",
            guests: []
        }
    }
}


export default Create;