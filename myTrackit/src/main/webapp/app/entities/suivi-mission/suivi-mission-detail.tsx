import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './suivi-mission.reducer';

export const SuiviMissionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const suiviMissionEntity = useAppSelector(state => state.mytrackit.suiviMission.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="suiviMissionDetailsHeading">
          <Translate contentKey="myTrackitApp.suiviMission.detail.title">SuiviMission</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{suiviMissionEntity.id}</dd>
          <dt>
            <span id="problemeConstate">
              <Translate contentKey="myTrackitApp.suiviMission.problemeConstate">Probleme Constate</Translate>
            </span>
          </dt>
          <dd>{suiviMissionEntity.problemeConstate}</dd>
          <dt>
            <span id="actionRecommandee">
              <Translate contentKey="myTrackitApp.suiviMission.actionRecommandee">Action Recommandee</Translate>
            </span>
          </dt>
          <dd>{suiviMissionEntity.actionRecommandee}</dd>
          <dt>
            <span id="dateEcheance">
              <Translate contentKey="myTrackitApp.suiviMission.dateEcheance">Date Echeance</Translate>
            </span>
          </dt>
          <dd>{suiviMissionEntity.dateEcheance}</dd>
        </dl>
        <Button tag={Link} to="/suivi-mission" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/suivi-mission/${suiviMissionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SuiviMissionDetail;
