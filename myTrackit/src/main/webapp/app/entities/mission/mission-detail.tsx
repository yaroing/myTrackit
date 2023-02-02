import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './mission.reducer';

export const MissionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const missionEntity = useAppSelector(state => state.mytrackit.mission.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="missionDetailsHeading">
          <Translate contentKey="myTrackitApp.mission.detail.title">Mission</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{missionEntity.id}</dd>
          <dt>
            <span id="dateMission">
              <Translate contentKey="myTrackitApp.mission.dateMission">Date Mission</Translate>
            </span>
          </dt>
          <dd>
            {missionEntity.dateMission ? <TextFormat value={missionEntity.dateMission} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="dateDebut">
              <Translate contentKey="myTrackitApp.mission.dateDebut">Date Debut</Translate>
            </span>
          </dt>
          <dd>{missionEntity.dateDebut ? <TextFormat value={missionEntity.dateDebut} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="dateFin">
              <Translate contentKey="myTrackitApp.mission.dateFin">Date Fin</Translate>
            </span>
          </dt>
          <dd>{missionEntity.dateFin ? <TextFormat value={missionEntity.dateFin} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="rapportMission">
              <Translate contentKey="myTrackitApp.mission.rapportMission">Rapport Mission</Translate>
            </span>
          </dt>
          <dd>
            {missionEntity.rapportMission ? (
              <div>
                {missionEntity.rapportMissionContentType ? (
                  <a onClick={openFile(missionEntity.rapportMissionContentType, missionEntity.rapportMission)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                ) : null}
                <span>
                  {missionEntity.rapportMissionContentType}, {byteSize(missionEntity.rapportMission)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="debutMission">
              <Translate contentKey="myTrackitApp.mission.debutMission">Debut Mission</Translate>
            </span>
          </dt>
          <dd>
            {missionEntity.debutMission ? <TextFormat value={missionEntity.debutMission} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="finMission">
              <Translate contentKey="myTrackitApp.mission.finMission">Fin Mission</Translate>
            </span>
          </dt>
          <dd>{missionEntity.finMission ? <TextFormat value={missionEntity.finMission} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="field10">
              <Translate contentKey="myTrackitApp.mission.field10">Field 10</Translate>
            </span>
          </dt>
          <dd>{missionEntity.field10}</dd>
          <dt>
            <span id="fin">
              <Translate contentKey="myTrackitApp.mission.fin">Fin</Translate>
            </span>
          </dt>
          <dd>{missionEntity.fin}</dd>
        </dl>
        <Button tag={Link} to="/mission" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/mission/${missionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MissionDetail;
