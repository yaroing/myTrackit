import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './partenaire.reducer';

export const PartenaireDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const partenaireEntity = useAppSelector(state => state.mytrackit.partenaire.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="partenaireDetailsHeading">
          <Translate contentKey="myTrackitApp.partenaire.detail.title">Partenaire</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{partenaireEntity.id}</dd>
          <dt>
            <span id="nomPartenaire">
              <Translate contentKey="myTrackitApp.partenaire.nomPartenaire">Nom Partenaire</Translate>
            </span>
          </dt>
          <dd>{partenaireEntity.nomPartenaire}</dd>
          <dt>
            <span id="autreNom">
              <Translate contentKey="myTrackitApp.partenaire.autreNom">Autre Nom</Translate>
            </span>
          </dt>
          <dd>{partenaireEntity.autreNom}</dd>
          <dt>
            <span id="logPhone">
              <Translate contentKey="myTrackitApp.partenaire.logPhone">Log Phone</Translate>
            </span>
          </dt>
          <dd>{partenaireEntity.logPhone}</dd>
          <dt>
            <span id="emailPartenaire">
              <Translate contentKey="myTrackitApp.partenaire.emailPartenaire">Email Partenaire</Translate>
            </span>
          </dt>
          <dd>{partenaireEntity.emailPartenaire}</dd>
          <dt>
            <span id="locPartenaire">
              <Translate contentKey="myTrackitApp.partenaire.locPartenaire">Loc Partenaire</Translate>
            </span>
          </dt>
          <dd>{partenaireEntity.locPartenaire}</dd>
        </dl>
        <Button tag={Link} to="/partenaire" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/partenaire/${partenaireEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PartenaireDetail;
