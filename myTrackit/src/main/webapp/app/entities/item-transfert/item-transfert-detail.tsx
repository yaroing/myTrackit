import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './item-transfert.reducer';

export const ItemTransfertDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const itemTransfertEntity = useAppSelector(state => state.mytrackit.itemTransfert.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="itemTransfertDetailsHeading">
          <Translate contentKey="myTrackitApp.itemTransfert.detail.title">ItemTransfert</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{itemTransfertEntity.id}</dd>
          <dt>
            <span id="roDate">
              <Translate contentKey="myTrackitApp.itemTransfert.roDate">Ro Date</Translate>
            </span>
          </dt>
          <dd>
            {itemTransfertEntity.roDate ? <TextFormat value={itemTransfertEntity.roDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="matDesc">
              <Translate contentKey="myTrackitApp.itemTransfert.matDesc">Mat Desc</Translate>
            </span>
          </dt>
          <dd>{itemTransfertEntity.matDesc}</dd>
          <dt>
            <span id="unit">
              <Translate contentKey="myTrackitApp.itemTransfert.unit">Unit</Translate>
            </span>
          </dt>
          <dd>{itemTransfertEntity.unit}</dd>
          <dt>
            <span id="delQty">
              <Translate contentKey="myTrackitApp.itemTransfert.delQty">Del Qty</Translate>
            </span>
          </dt>
          <dd>{itemTransfertEntity.delQty}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="myTrackitApp.itemTransfert.value">Value</Translate>
            </span>
          </dt>
          <dd>{itemTransfertEntity.value}</dd>
          <dt>
            <span id="batch">
              <Translate contentKey="myTrackitApp.itemTransfert.batch">Batch</Translate>
            </span>
          </dt>
          <dd>{itemTransfertEntity.batch}</dd>
          <dt>
            <span id="bbDate">
              <Translate contentKey="myTrackitApp.itemTransfert.bbDate">Bb Date</Translate>
            </span>
          </dt>
          <dd>
            {itemTransfertEntity.bbDate ? <TextFormat value={itemTransfertEntity.bbDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="weight">
              <Translate contentKey="myTrackitApp.itemTransfert.weight">Weight</Translate>
            </span>
          </dt>
          <dd>{itemTransfertEntity.weight}</dd>
          <dt>
            <span id="volume">
              <Translate contentKey="myTrackitApp.itemTransfert.volume">Volume</Translate>
            </span>
          </dt>
          <dd>{itemTransfertEntity.volume}</dd>
          <dt>
            <span id="recQty">
              <Translate contentKey="myTrackitApp.itemTransfert.recQty">Rec Qty</Translate>
            </span>
          </dt>
          <dd>{itemTransfertEntity.recQty}</dd>
          <dt>
            <Translate contentKey="myTrackitApp.itemTransfert.transfert">Transfert</Translate>
          </dt>
          <dd>{itemTransfertEntity.transfert ? itemTransfertEntity.transfert.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/item-transfert" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/item-transfert/${itemTransfertEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ItemTransfertDetail;
