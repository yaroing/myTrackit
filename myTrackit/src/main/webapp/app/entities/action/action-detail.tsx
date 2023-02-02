import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './action.reducer';

export const ActionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const actionEntity = useAppSelector(state => state.mytrackit.action.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="actionDetailsHeading">
          <Translate contentKey="myTrackitApp.action.detail.title">Action</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{actionEntity.id}</dd>
          <dt>
            <span id="dateAction">
              <Translate contentKey="myTrackitApp.action.dateAction">Date Action</Translate>
            </span>
          </dt>
          <dd>{actionEntity.dateAction ? <TextFormat value={actionEntity.dateAction} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="rapportAction">
              <Translate contentKey="myTrackitApp.action.rapportAction">Rapport Action</Translate>
            </span>
          </dt>
          <dd>{actionEntity.rapportAction}</dd>
          <dt>
            <Translate contentKey="myTrackitApp.action.transfert">Transfert</Translate>
          </dt>
          <dd>{actionEntity.transfert ? actionEntity.transfert.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/action" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/action/${actionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ActionDetail;
