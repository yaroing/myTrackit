import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './transporteur.reducer';

export const TransporteurDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const transporteurEntity = useAppSelector(state => state.mytrackit.transporteur.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="transporteurDetailsHeading">
          <Translate contentKey="myTrackitApp.transporteur.detail.title">Transporteur</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{transporteurEntity.id}</dd>
          <dt>
            <span id="nomTransporteur">
              <Translate contentKey="myTrackitApp.transporteur.nomTransporteur">Nom Transporteur</Translate>
            </span>
          </dt>
          <dd>{transporteurEntity.nomTransporteur}</dd>
          <dt>
            <span id="nomDirecteur">
              <Translate contentKey="myTrackitApp.transporteur.nomDirecteur">Nom Directeur</Translate>
            </span>
          </dt>
          <dd>{transporteurEntity.nomDirecteur}</dd>
          <dt>
            <span id="phoneTransporteur">
              <Translate contentKey="myTrackitApp.transporteur.phoneTransporteur">Phone Transporteur</Translate>
            </span>
          </dt>
          <dd>{transporteurEntity.phoneTransporteur}</dd>
          <dt>
            <span id="emailTransporteur">
              <Translate contentKey="myTrackitApp.transporteur.emailTransporteur">Email Transporteur</Translate>
            </span>
          </dt>
          <dd>{transporteurEntity.emailTransporteur}</dd>
        </dl>
        <Button tag={Link} to="/transporteur" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/transporteur/${transporteurEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TransporteurDetail;
