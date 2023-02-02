import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './details-requete.reducer';

export const DetailsRequeteDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const detailsRequeteEntity = useAppSelector(state => state.mytrackit.detailsRequete.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="detailsRequeteDetailsHeading">
          <Translate contentKey="myTrackitApp.detailsRequete.detail.title">DetailsRequete</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{detailsRequeteEntity.id}</dd>
          <dt>
            <span id="quantiteDemandee">
              <Translate contentKey="myTrackitApp.detailsRequete.quantiteDemandee">Quantite Demandee</Translate>
            </span>
          </dt>
          <dd>{detailsRequeteEntity.quantiteDemandee}</dd>
          <dt>
            <span id="quantiteApprouvee">
              <Translate contentKey="myTrackitApp.detailsRequete.quantiteApprouvee">Quantite Approuvee</Translate>
            </span>
          </dt>
          <dd>{detailsRequeteEntity.quantiteApprouvee}</dd>
          <dt>
            <span id="quantiteRecue">
              <Translate contentKey="myTrackitApp.detailsRequete.quantiteRecue">Quantite Recue</Translate>
            </span>
          </dt>
          <dd>{detailsRequeteEntity.quantiteRecue}</dd>
          <dt>
            <span id="itemObs">
              <Translate contentKey="myTrackitApp.detailsRequete.itemObs">Item Obs</Translate>
            </span>
          </dt>
          <dd>{detailsRequeteEntity.itemObs}</dd>
          <dt>
            <Translate contentKey="myTrackitApp.detailsRequete.requetePartenaire">Requete Partenaire</Translate>
          </dt>
          <dd>{detailsRequeteEntity.requetePartenaire ? detailsRequeteEntity.requetePartenaire.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/details-requete" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/details-requete/${detailsRequeteEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DetailsRequeteDetail;
