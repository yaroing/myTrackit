import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './item-verifie.reducer';

export const ItemVerifieDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const itemVerifieEntity = useAppSelector(state => state.mytrackit.itemVerifie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="itemVerifieDetailsHeading">
          <Translate contentKey="myTrackitApp.itemVerifie.detail.title">ItemVerifie</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{itemVerifieEntity.id}</dd>
          <dt>
            <span id="quantiteTransfert">
              <Translate contentKey="myTrackitApp.itemVerifie.quantiteTransfert">Quantite Transfert</Translate>
            </span>
          </dt>
          <dd>{itemVerifieEntity.quantiteTransfert}</dd>
          <dt>
            <span id="quantiteRecu">
              <Translate contentKey="myTrackitApp.itemVerifie.quantiteRecu">Quantite Recu</Translate>
            </span>
          </dt>
          <dd>{itemVerifieEntity.quantiteRecu}</dd>
          <dt>
            <span id="quantiteUtilisee">
              <Translate contentKey="myTrackitApp.itemVerifie.quantiteUtilisee">Quantite Utilisee</Translate>
            </span>
          </dt>
          <dd>{itemVerifieEntity.quantiteUtilisee}</dd>
          <dt>
            <span id="quantiteDisponible">
              <Translate contentKey="myTrackitApp.itemVerifie.quantiteDisponible">Quantite Disponible</Translate>
            </span>
          </dt>
          <dd>{itemVerifieEntity.quantiteDisponible}</dd>
          <dt>
            <span id="quantiteEcart">
              <Translate contentKey="myTrackitApp.itemVerifie.quantiteEcart">Quantite Ecart</Translate>
            </span>
          </dt>
          <dd>{itemVerifieEntity.quantiteEcart}</dd>
          <dt>
            <Translate contentKey="myTrackitApp.itemVerifie.mission">Mission</Translate>
          </dt>
          <dd>{itemVerifieEntity.mission ? itemVerifieEntity.mission.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/item-verifie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/item-verifie/${itemVerifieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ItemVerifieDetail;
