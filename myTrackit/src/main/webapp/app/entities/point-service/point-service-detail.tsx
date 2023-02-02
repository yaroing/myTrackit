import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './point-service.reducer';

export const PointServiceDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const pointServiceEntity = useAppSelector(state => state.mytrackit.pointService.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="pointServiceDetailsHeading">
          <Translate contentKey="myTrackitApp.pointService.detail.title">PointService</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{pointServiceEntity.id}</dd>
          <dt>
            <span id="nomPos">
              <Translate contentKey="myTrackitApp.pointService.nomPos">Nom Pos</Translate>
            </span>
          </dt>
          <dd>{pointServiceEntity.nomPos}</dd>
          <dt>
            <span id="posLon">
              <Translate contentKey="myTrackitApp.pointService.posLon">Pos Lon</Translate>
            </span>
          </dt>
          <dd>{pointServiceEntity.posLon}</dd>
          <dt>
            <span id="posLat">
              <Translate contentKey="myTrackitApp.pointService.posLat">Pos Lat</Translate>
            </span>
          </dt>
          <dd>{pointServiceEntity.posLat}</dd>
          <dt>
            <span id="posContact">
              <Translate contentKey="myTrackitApp.pointService.posContact">Pos Contact</Translate>
            </span>
          </dt>
          <dd>{pointServiceEntity.posContact}</dd>
          <dt>
            <span id="posGsm">
              <Translate contentKey="myTrackitApp.pointService.posGsm">Pos Gsm</Translate>
            </span>
          </dt>
          <dd>{pointServiceEntity.posGsm}</dd>
        </dl>
        <Button tag={Link} to="/point-service" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/point-service/${pointServiceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PointServiceDetail;
