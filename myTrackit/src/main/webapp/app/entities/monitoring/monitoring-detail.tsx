import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './monitoring.reducer';

export const MonitoringDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const monitoringEntity = useAppSelector(state => state.mytrackit.monitoring.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="monitoringDetailsHeading">
          <Translate contentKey="myTrackitApp.monitoring.detail.title">Monitoring</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{monitoringEntity.id}</dd>
          <dt>
            <span id="atpeAnnee">
              <Translate contentKey="myTrackitApp.monitoring.atpeAnnee">Atpe Annee</Translate>
            </span>
          </dt>
          <dd>{monitoringEntity.atpeAnnee}</dd>
          <dt>
            <span id="atpeMois">
              <Translate contentKey="myTrackitApp.monitoring.atpeMois">Atpe Mois</Translate>
            </span>
          </dt>
          <dd>{monitoringEntity.atpeMois}</dd>
          <dt>
            <span id="atpeStock">
              <Translate contentKey="myTrackitApp.monitoring.atpeStock">Atpe Stock</Translate>
            </span>
          </dt>
          <dd>{monitoringEntity.atpeStock}</dd>
          <dt>
            <span id="atpeDispo">
              <Translate contentKey="myTrackitApp.monitoring.atpeDispo">Atpe Dispo</Translate>
            </span>
          </dt>
          <dd>{monitoringEntity.atpeDispo}</dd>
          <dt>
            <span id="atpeEndom">
              <Translate contentKey="myTrackitApp.monitoring.atpeEndom">Atpe Endom</Translate>
            </span>
          </dt>
          <dd>{monitoringEntity.atpeEndom}</dd>
          <dt>
            <span id="atpePerime">
              <Translate contentKey="myTrackitApp.monitoring.atpePerime">Atpe Perime</Translate>
            </span>
          </dt>
          <dd>{monitoringEntity.atpePerime}</dd>
          <dt>
            <span id="atpeRupture">
              <Translate contentKey="myTrackitApp.monitoring.atpeRupture">Atpe Rupture</Translate>
            </span>
          </dt>
          <dd>{monitoringEntity.atpeRupture}</dd>
          <dt>
            <span id="atpeNjour">
              <Translate contentKey="myTrackitApp.monitoring.atpeNjour">Atpe Njour</Translate>
            </span>
          </dt>
          <dd>{monitoringEntity.atpeNjour}</dd>
          <dt>
            <span id="atpeMagasin">
              <Translate contentKey="myTrackitApp.monitoring.atpeMagasin">Atpe Magasin</Translate>
            </span>
          </dt>
          <dd>{monitoringEntity.atpeMagasin}</dd>
          <dt>
            <span id="atpePalette">
              <Translate contentKey="myTrackitApp.monitoring.atpePalette">Atpe Palette</Translate>
            </span>
          </dt>
          <dd>{monitoringEntity.atpePalette}</dd>
          <dt>
            <span id="atpePosition">
              <Translate contentKey="myTrackitApp.monitoring.atpePosition">Atpe Position</Translate>
            </span>
          </dt>
          <dd>{monitoringEntity.atpePosition}</dd>
          <dt>
            <span id="atpeHauteur">
              <Translate contentKey="myTrackitApp.monitoring.atpeHauteur">Atpe Hauteur</Translate>
            </span>
          </dt>
          <dd>{monitoringEntity.atpeHauteur}</dd>
          <dt>
            <span id="atpePersonnel">
              <Translate contentKey="myTrackitApp.monitoring.atpePersonnel">Atpe Personnel</Translate>
            </span>
          </dt>
          <dd>{monitoringEntity.atpePersonnel}</dd>
          <dt>
            <span id="atpeAdmission">
              <Translate contentKey="myTrackitApp.monitoring.atpeAdmission">Atpe Admission</Translate>
            </span>
          </dt>
          <dd>{monitoringEntity.atpeAdmission}</dd>
          <dt>
            <span id="atpeSortie">
              <Translate contentKey="myTrackitApp.monitoring.atpeSortie">Atpe Sortie</Translate>
            </span>
          </dt>
          <dd>{monitoringEntity.atpeSortie}</dd>
          <dt>
            <span id="atpeGueris">
              <Translate contentKey="myTrackitApp.monitoring.atpeGueris">Atpe Gueris</Translate>
            </span>
          </dt>
          <dd>{monitoringEntity.atpeGueris}</dd>
          <dt>
            <span id="atpeAbandon">
              <Translate contentKey="myTrackitApp.monitoring.atpeAbandon">Atpe Abandon</Translate>
            </span>
          </dt>
          <dd>{monitoringEntity.atpeAbandon}</dd>
          <dt>
            <span id="atpePoids">
              <Translate contentKey="myTrackitApp.monitoring.atpePoids">Atpe Poids</Translate>
            </span>
          </dt>
          <dd>{monitoringEntity.atpePoids}</dd>
          <dt>
            <span id="atpeTrasnsfert">
              <Translate contentKey="myTrackitApp.monitoring.atpeTrasnsfert">Atpe Trasnsfert</Translate>
            </span>
          </dt>
          <dd>{monitoringEntity.atpeTrasnsfert}</dd>
          <dt>
            <span id="atpeParent">
              <Translate contentKey="myTrackitApp.monitoring.atpeParent">Atpe Parent</Translate>
            </span>
          </dt>
          <dd>{monitoringEntity.atpeParent}</dd>
          <dt>
            <Translate contentKey="myTrackitApp.monitoring.pointService">Point Service</Translate>
          </dt>
          <dd>{monitoringEntity.pointService ? monitoringEntity.pointService.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/monitoring" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/monitoring/${monitoringEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MonitoringDetail;
