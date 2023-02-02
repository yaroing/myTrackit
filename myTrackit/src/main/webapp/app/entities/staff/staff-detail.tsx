import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './staff.reducer';

export const StaffDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const staffEntity = useAppSelector(state => state.mytrackit.staff.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="staffDetailsHeading">
          <Translate contentKey="myTrackitApp.staff.detail.title">Staff</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{staffEntity.id}</dd>
          <dt>
            <span id="staffFname">
              <Translate contentKey="myTrackitApp.staff.staffFname">Staff Fname</Translate>
            </span>
          </dt>
          <dd>{staffEntity.staffFname}</dd>
          <dt>
            <span id="staffLname">
              <Translate contentKey="myTrackitApp.staff.staffLname">Staff Lname</Translate>
            </span>
          </dt>
          <dd>{staffEntity.staffLname}</dd>
          <dt>
            <span id="staffTitle">
              <Translate contentKey="myTrackitApp.staff.staffTitle">Staff Title</Translate>
            </span>
          </dt>
          <dd>{staffEntity.staffTitle}</dd>
          <dt>
            <span id="staffName">
              <Translate contentKey="myTrackitApp.staff.staffName">Staff Name</Translate>
            </span>
          </dt>
          <dd>{staffEntity.staffName}</dd>
          <dt>
            <span id="staffEmail">
              <Translate contentKey="myTrackitApp.staff.staffEmail">Staff Email</Translate>
            </span>
          </dt>
          <dd>{staffEntity.staffEmail}</dd>
          <dt>
            <span id="staffPhone">
              <Translate contentKey="myTrackitApp.staff.staffPhone">Staff Phone</Translate>
            </span>
          </dt>
          <dd>{staffEntity.staffPhone}</dd>
        </dl>
        <Button tag={Link} to="/staff" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/staff/${staffEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default StaffDetail;
