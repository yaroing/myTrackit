import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './stock-partenaire.reducer';

export const StockPartenaireDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const stockPartenaireEntity = useAppSelector(state => state.mytrackit.stockPartenaire.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="stockPartenaireDetailsHeading">
          <Translate contentKey="myTrackitApp.stockPartenaire.detail.title">StockPartenaire</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{stockPartenaireEntity.id}</dd>
          <dt>
            <span id="stockAnnee">
              <Translate contentKey="myTrackitApp.stockPartenaire.stockAnnee">Stock Annee</Translate>
            </span>
          </dt>
          <dd>{stockPartenaireEntity.stockAnnee}</dd>
          <dt>
            <span id="stockMois">
              <Translate contentKey="myTrackitApp.stockPartenaire.stockMois">Stock Mois</Translate>
            </span>
          </dt>
          <dd>{stockPartenaireEntity.stockMois}</dd>
          <dt>
            <span id="entreeMois">
              <Translate contentKey="myTrackitApp.stockPartenaire.entreeMois">Entree Mois</Translate>
            </span>
          </dt>
          <dd>{stockPartenaireEntity.entreeMois}</dd>
          <dt>
            <span id="sortieMois">
              <Translate contentKey="myTrackitApp.stockPartenaire.sortieMois">Sortie Mois</Translate>
            </span>
          </dt>
          <dd>{stockPartenaireEntity.sortieMois}</dd>
          <dt>
            <span id="stockFinmois">
              <Translate contentKey="myTrackitApp.stockPartenaire.stockFinmois">Stock Finmois</Translate>
            </span>
          </dt>
          <dd>{stockPartenaireEntity.stockFinmois}</dd>
          <dt>
            <span id="stockDebut">
              <Translate contentKey="myTrackitApp.stockPartenaire.stockDebut">Stock Debut</Translate>
            </span>
          </dt>
          <dd>{stockPartenaireEntity.stockDebut}</dd>
        </dl>
        <Button tag={Link} to="/stock-partenaire" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/stock-partenaire/${stockPartenaireEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default StockPartenaireDetail;
