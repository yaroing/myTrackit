import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './requete-partenaire.reducer';

export const RequetePartenaireDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const requetePartenaireEntity = useAppSelector(state => state.mytrackit.requetePartenaire.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="requetePartenaireDetailsHeading">
          <Translate contentKey="myTrackitApp.requetePartenaire.detail.title">RequetePartenaire</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{requetePartenaireEntity.id}</dd>
          <dt>
            <span id="requeteDate">
              <Translate contentKey="myTrackitApp.requetePartenaire.requeteDate">Requete Date</Translate>
            </span>
          </dt>
          <dd>
            {requetePartenaireEntity.requeteDate ? (
              <TextFormat value={requetePartenaireEntity.requeteDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="fichierAtache">
              <Translate contentKey="myTrackitApp.requetePartenaire.fichierAtache">Fichier Atache</Translate>
            </span>
          </dt>
          <dd>
            {requetePartenaireEntity.fichierAtache ? (
              <div>
                {requetePartenaireEntity.fichierAtacheContentType ? (
                  <a onClick={openFile(requetePartenaireEntity.fichierAtacheContentType, requetePartenaireEntity.fichierAtache)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                ) : null}
                <span>
                  {requetePartenaireEntity.fichierAtacheContentType}, {byteSize(requetePartenaireEntity.fichierAtache)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="requeteObs">
              <Translate contentKey="myTrackitApp.requetePartenaire.requeteObs">Requete Obs</Translate>
            </span>
          </dt>
          <dd>{requetePartenaireEntity.requeteObs}</dd>
          <dt>
            <span id="reqTraitee">
              <Translate contentKey="myTrackitApp.requetePartenaire.reqTraitee">Req Traitee</Translate>
            </span>
          </dt>
          <dd>{requetePartenaireEntity.reqTraitee}</dd>
        </dl>
        <Button tag={Link} to="/requete-partenaire" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/requete-partenaire/${requetePartenaireEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RequetePartenaireDetail;
