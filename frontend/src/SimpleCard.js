import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';

const styles = {
    card: {
        minWidth: 275,
    },
    bullet: {
        display: 'inline-block',
        margin: '0 2px',
        transform: 'scale(0.8)',
    },
    title: {
        fontSize: 14,
    },
    pos: {
        marginBottom: 12,
    },
};

function SimpleCard(props) {
    const { classes } = props;
    return (
        <Card className={classes.card}>
            <CardContent>
                <Typography className={classes.title} color="textSecondary" gutterBottom>
                    {props.status}
                </Typography>
                <Typography variant="h5" component="h2">
                    {props.value.title}
                </Typography>
                <Typography className={classes.pos} color="textSecondary">
                    {props.value.location}
                </Typography>
                <Typography component="p">
                    Created by: {props.value.createdBy}
                </Typography>
            </CardContent>
            <CardActions>
                <Button size="small" onClick={props.acceptedClick}>Accept</Button>
                <Button size="small" onClick={props.deniedClick}>Deny</Button>
            </CardActions>
        </Card>
    );
}

SimpleCard.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(SimpleCard);
