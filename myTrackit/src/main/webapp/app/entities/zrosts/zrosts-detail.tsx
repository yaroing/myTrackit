import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './zrosts.reducer';

export const ZrostsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const zrostsEntity = useAppSelector(state => state.mytrackit.zrosts.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="zrostsDetailsHeading">
          <Translate contentKey="myTrackitApp.zrosts.detail.title">Zrosts</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.id}</dd>
          <dt>
            <span id="roId">
              <Translate contentKey="myTrackitApp.zrosts.roId">Ro Id</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.roId}</dd>
          <dt>
            <span id="roItem">
              <Translate contentKey="myTrackitApp.zrosts.roItem">Ro Item</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.roItem}</dd>
          <dt>
            <span id="roDate">
              <Translate contentKey="myTrackitApp.zrosts.roDate">Ro Date</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.roDate ? <TextFormat value={zrostsEntity.roDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="roTdd">
              <Translate contentKey="myTrackitApp.zrosts.roTdd">Ro Tdd</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.roTdd ? <TextFormat value={zrostsEntity.roTdd} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="materialId">
              <Translate contentKey="myTrackitApp.zrosts.materialId">Material Id</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.materialId}</dd>
          <dt>
            <span id="matDesc">
              <Translate contentKey="myTrackitApp.zrosts.matDesc">Mat Desc</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.matDesc}</dd>
          <dt>
            <span id="delQty">
              <Translate contentKey="myTrackitApp.zrosts.delQty">Del Qty</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.delQty}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="myTrackitApp.zrosts.value">Value</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.value}</dd>
          <dt>
            <span id="storageLoc">
              <Translate contentKey="myTrackitApp.zrosts.storageLoc">Storage Loc</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.storageLoc}</dd>
          <dt>
            <span id="whId">
              <Translate contentKey="myTrackitApp.zrosts.whId">Wh Id</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.whId}</dd>
          <dt>
            <span id="whDesc">
              <Translate contentKey="myTrackitApp.zrosts.whDesc">Wh Desc</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.whDesc}</dd>
          <dt>
            <span id="consId">
              <Translate contentKey="myTrackitApp.zrosts.consId">Cons Id</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.consId}</dd>
          <dt>
            <span id="consName">
              <Translate contentKey="myTrackitApp.zrosts.consName">Cons Name</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.consName}</dd>
          <dt>
            <span id="authPerson">
              <Translate contentKey="myTrackitApp.zrosts.authPerson">Auth Person</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.authPerson}</dd>
          <dt>
            <span id="soId">
              <Translate contentKey="myTrackitApp.zrosts.soId">So Id</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.soId}</dd>
          <dt>
            <span id="poId">
              <Translate contentKey="myTrackitApp.zrosts.poId">Po Id</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.poId}</dd>
          <dt>
            <span id="delivery">
              <Translate contentKey="myTrackitApp.zrosts.delivery">Delivery</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.delivery}</dd>
          <dt>
            <span id="grant">
              <Translate contentKey="myTrackitApp.zrosts.grant">Grant</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.grant}</dd>
          <dt>
            <span id="wbs">
              <Translate contentKey="myTrackitApp.zrosts.wbs">Wbs</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.wbs}</dd>
          <dt>
            <span id="pickStatus">
              <Translate contentKey="myTrackitApp.zrosts.pickStatus">Pick Status</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.pickStatus}</dd>
          <dt>
            <span id="toNumber">
              <Translate contentKey="myTrackitApp.zrosts.toNumber">To Number</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.toNumber}</dd>
          <dt>
            <span id="trsptStatus">
              <Translate contentKey="myTrackitApp.zrosts.trsptStatus">Trspt Status</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.trsptStatus}</dd>
          <dt>
            <span id="waybId">
              <Translate contentKey="myTrackitApp.zrosts.waybId">Wayb Id</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.waybId}</dd>
          <dt>
            <span id="trsptrName">
              <Translate contentKey="myTrackitApp.zrosts.trsptrName">Trsptr Name</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.trsptrName}</dd>
          <dt>
            <span id="shipmtEd">
              <Translate contentKey="myTrackitApp.zrosts.shipmtEd">Shipmt Ed</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.shipmtEd ? <TextFormat value={zrostsEntity.shipmtEd} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="gdsStatus">
              <Translate contentKey="myTrackitApp.zrosts.gdsStatus">Gds Status</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.gdsStatus}</dd>
          <dt>
            <span id="gdsDate">
              <Translate contentKey="myTrackitApp.zrosts.gdsDate">Gds Date</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.gdsDate ? <TextFormat value={zrostsEntity.gdsDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="roSubitem">
              <Translate contentKey="myTrackitApp.zrosts.roSubitem">Ro Subitem</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.roSubitem}</dd>
          <dt>
            <span id="roType">
              <Translate contentKey="myTrackitApp.zrosts.roType">Ro Type</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.roType}</dd>
          <dt>
            <span id="unit">
              <Translate contentKey="myTrackitApp.zrosts.unit">Unit</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.unit}</dd>
          <dt>
            <span id="movingPrice">
              <Translate contentKey="myTrackitApp.zrosts.movingPrice">Moving Price</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.movingPrice}</dd>
          <dt>
            <span id="plantId">
              <Translate contentKey="myTrackitApp.zrosts.plantId">Plant Id</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.plantId}</dd>
          <dt>
            <span id="plantName">
              <Translate contentKey="myTrackitApp.zrosts.plantName">Plant Name</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.plantName}</dd>
          <dt>
            <span id="storageLocp">
              <Translate contentKey="myTrackitApp.zrosts.storageLocp">Storage Locp</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.storageLocp}</dd>
          <dt>
            <span id="dwhId">
              <Translate contentKey="myTrackitApp.zrosts.dwhId">Dwh Id</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.dwhId}</dd>
          <dt>
            <span id="dwhDesc">
              <Translate contentKey="myTrackitApp.zrosts.dwhDesc">Dwh Desc</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.dwhDesc}</dd>
          <dt>
            <span id="shipParty">
              <Translate contentKey="myTrackitApp.zrosts.shipParty">Ship Party</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.shipParty}</dd>
          <dt>
            <span id="trsptMeans">
              <Translate contentKey="myTrackitApp.zrosts.trsptMeans">Trspt Means</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.trsptMeans}</dd>
          <dt>
            <span id="progOfficer">
              <Translate contentKey="myTrackitApp.zrosts.progOfficer">Prog Officer</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.progOfficer}</dd>
          <dt>
            <span id="soItems">
              <Translate contentKey="myTrackitApp.zrosts.soItems">So Items</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.soItems}</dd>
          <dt>
            <span id="poItems">
              <Translate contentKey="myTrackitApp.zrosts.poItems">Po Items</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.poItems}</dd>
          <dt>
            <span id="trsptrId">
              <Translate contentKey="myTrackitApp.zrosts.trsptrId">Trsptr Id</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.trsptrId}</dd>
          <dt>
            <span id="gdsId">
              <Translate contentKey="myTrackitApp.zrosts.gdsId">Gds Id</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.gdsId}</dd>
          <dt>
            <span id="gdsItem">
              <Translate contentKey="myTrackitApp.zrosts.gdsItem">Gds Item</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.gdsItem}</dd>
          <dt>
            <span id="batch">
              <Translate contentKey="myTrackitApp.zrosts.batch">Batch</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.batch}</dd>
          <dt>
            <span id="bbDate">
              <Translate contentKey="myTrackitApp.zrosts.bbDate">Bb Date</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.bbDate ? <TextFormat value={zrostsEntity.bbDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="planningDate">
              <Translate contentKey="myTrackitApp.zrosts.planningDate">Planning Date</Translate>
            </span>
          </dt>
          <dd>
            {zrostsEntity.planningDate ? <TextFormat value={zrostsEntity.planningDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="checkinDate">
              <Translate contentKey="myTrackitApp.zrosts.checkinDate">Checkin Date</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.checkinDate ? <TextFormat value={zrostsEntity.checkinDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="shipmentSdate">
              <Translate contentKey="myTrackitApp.zrosts.shipmentSdate">Shipment Sdate</Translate>
            </span>
          </dt>
          <dd>
            {zrostsEntity.shipmentSdate ? <TextFormat value={zrostsEntity.shipmentSdate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="loadingSdate">
              <Translate contentKey="myTrackitApp.zrosts.loadingSdate">Loading Sdate</Translate>
            </span>
          </dt>
          <dd>
            {zrostsEntity.loadingSdate ? <TextFormat value={zrostsEntity.loadingSdate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="loadingEdate">
              <Translate contentKey="myTrackitApp.zrosts.loadingEdate">Loading Edate</Translate>
            </span>
          </dt>
          <dd>
            {zrostsEntity.loadingEdate ? <TextFormat value={zrostsEntity.loadingEdate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="ashipmentSdate">
              <Translate contentKey="myTrackitApp.zrosts.ashipmentSdate">Ashipment Sdate</Translate>
            </span>
          </dt>
          <dd>
            {zrostsEntity.ashipmentSdate ? <TextFormat value={zrostsEntity.ashipmentSdate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="shipmentCdate">
              <Translate contentKey="myTrackitApp.zrosts.shipmentCdate">Shipment Cdate</Translate>
            </span>
          </dt>
          <dd>
            {zrostsEntity.shipmentCdate ? <TextFormat value={zrostsEntity.shipmentCdate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="weight">
              <Translate contentKey="myTrackitApp.zrosts.weight">Weight</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.weight}</dd>
          <dt>
            <span id="volume">
              <Translate contentKey="myTrackitApp.zrosts.volume">Volume</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.volume}</dd>
          <dt>
            <span id="section">
              <Translate contentKey="myTrackitApp.zrosts.section">Section</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.section}</dd>
          <dt>
            <span id="commodityGroup">
              <Translate contentKey="myTrackitApp.zrosts.commodityGroup">Commodity Group</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.commodityGroup}</dd>
          <dt>
            <span id="region">
              <Translate contentKey="myTrackitApp.zrosts.region">Region</Translate>
            </span>
          </dt>
          <dd>{zrostsEntity.region}</dd>
        </dl>
        <Button tag={Link} to="/zrosts" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/zrosts/${zrostsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ZrostsDetail;
