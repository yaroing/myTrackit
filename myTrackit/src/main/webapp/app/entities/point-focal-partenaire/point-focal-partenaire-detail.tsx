import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './point-focal-partenaire.reducer';

export const PointFocalPartenaireDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const pointFocalPartenaireEntity = useAppSelector(state => state.mytrackit.pointFocalPartenaire.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="pointFocalPartenaireDetailsHeading">
          <Translate contentKey="myTrackitApp.pointFocalPartenaire.detail.title">PointFocalPartenaire</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{pointFocalPartenaireEntity.id}</dd>
          <dt>
            <span id="nomPf">
              <Translate contentKey="myTrackitApp.pointFocalPartenaire.nomPf">Nom Pf</Translate>
            </span>
          </dt>
          <dd>{pointFocalPartenaireEntity.nomPf}</dd>
          <dt>
            <span id="fonctionPf">
              <Translate contentKey="myTrackitApp.pointFocalPartenaire.fonctionPf">Fonction Pf</Translate>
            </span>
          </dt>
          <dd>{pointFocalPartenaireEntity.fonctionPf}</dd>
          <dt>
            <span id="gsmPf">
              <Translate contentKey="myTrackitApp.pointFocalPartenaire.gsmPf">Gsm Pf</Translate>
            </span>
          </dt>
          <dd>{pointFocalPartenaireEntity.gsmPf}</dd>
          <dt>
            <span id="emailPf">
              <Translate contentKey="myTrackitApp.pointFocalPartenaire.emailPf">Email Pf</Translate>
            </span>
          </dt>
          <dd>{pointFocalPartenaireEntity.emailPf}</dd>
        </dl>
        <Button tag={Link} to="/point-focal-partenaire" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/point-focal-partenaire/${pointFocalPartenaireEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PointFocalPartenaireDetail;
