import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './transfert.reducer';

export const TransfertDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const transfertEntity = useAppSelector(state => state.mytrackit.transfert.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="transfertDetailsHeading">
          <Translate contentKey="myTrackitApp.transfert.detail.title">Transfert</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{transfertEntity.id}</dd>
          <dt>
            <span id="dateExp">
              <Translate contentKey="myTrackitApp.transfert.dateExp">Date Exp</Translate>
            </span>
          </dt>
          <dd>{transfertEntity.dateExp ? <TextFormat value={transfertEntity.dateExp} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="nomChauffeur">
              <Translate contentKey="myTrackitApp.transfert.nomChauffeur">Nom Chauffeur</Translate>
            </span>
          </dt>
          <dd>{transfertEntity.nomChauffeur}</dd>
          <dt>
            <span id="dateRec">
              <Translate contentKey="myTrackitApp.transfert.dateRec">Date Rec</Translate>
            </span>
          </dt>
          <dd>{transfertEntity.dateRec ? <TextFormat value={transfertEntity.dateRec} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="cphone">
              <Translate contentKey="myTrackitApp.transfert.cphone">Cphone</Translate>
            </span>
          </dt>
          <dd>{transfertEntity.cphone}</dd>
        </dl>
        <Button tag={Link} to="/transfert" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/transfert/${transfertEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TransfertDetail;
