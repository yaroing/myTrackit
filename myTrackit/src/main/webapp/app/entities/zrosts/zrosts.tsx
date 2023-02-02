import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IZrosts } from 'app/shared/model/zrosts.model';
import { getEntities } from './zrosts.reducer';

export const Zrosts = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const zrostsList = useAppSelector(state => state.mytrackit.zrosts.entities);
  const loading = useAppSelector(state => state.mytrackit.zrosts.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="zrosts-heading" data-cy="ZrostsHeading">
        <Translate contentKey="myTrackitApp.zrosts.home.title">Zrosts</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="myTrackitApp.zrosts.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/zrosts/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="myTrackitApp.zrosts.home.createLabel">Create new Zrosts</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {zrostsList && zrostsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.roId">Ro Id</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.roItem">Ro Item</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.roDate">Ro Date</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.roTdd">Ro Tdd</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.materialId">Material Id</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.matDesc">Mat Desc</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.delQty">Del Qty</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.value">Value</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.storageLoc">Storage Loc</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.whId">Wh Id</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.whDesc">Wh Desc</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.consId">Cons Id</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.consName">Cons Name</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.authPerson">Auth Person</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.soId">So Id</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.poId">Po Id</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.delivery">Delivery</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.grant">Grant</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.wbs">Wbs</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.pickStatus">Pick Status</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.toNumber">To Number</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.trsptStatus">Trspt Status</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.waybId">Wayb Id</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.trsptrName">Trsptr Name</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.shipmtEd">Shipmt Ed</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.gdsStatus">Gds Status</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.gdsDate">Gds Date</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.roSubitem">Ro Subitem</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.roType">Ro Type</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.unit">Unit</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.movingPrice">Moving Price</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.plantId">Plant Id</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.plantName">Plant Name</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.storageLocp">Storage Locp</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.dwhId">Dwh Id</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.dwhDesc">Dwh Desc</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.shipParty">Ship Party</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.trsptMeans">Trspt Means</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.progOfficer">Prog Officer</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.soItems">So Items</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.poItems">Po Items</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.trsptrId">Trsptr Id</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.gdsId">Gds Id</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.gdsItem">Gds Item</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.batch">Batch</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.bbDate">Bb Date</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.planningDate">Planning Date</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.checkinDate">Checkin Date</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.shipmentSdate">Shipment Sdate</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.loadingSdate">Loading Sdate</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.loadingEdate">Loading Edate</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.ashipmentSdate">Ashipment Sdate</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.shipmentCdate">Shipment Cdate</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.weight">Weight</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.volume">Volume</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.section">Section</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.commodityGroup">Commodity Group</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.zrosts.region">Region</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {zrostsList.map((zrosts, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/zrosts/${zrosts.id}`} color="link" size="sm">
                      {zrosts.id}
                    </Button>
                  </td>
                  <td>{zrosts.roId}</td>
                  <td>{zrosts.roItem}</td>
                  <td>{zrosts.roDate ? <TextFormat type="date" value={zrosts.roDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{zrosts.roTdd ? <TextFormat type="date" value={zrosts.roTdd} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{zrosts.materialId}</td>
                  <td>{zrosts.matDesc}</td>
                  <td>{zrosts.delQty}</td>
                  <td>{zrosts.value}</td>
                  <td>{zrosts.storageLoc}</td>
                  <td>{zrosts.whId}</td>
                  <td>{zrosts.whDesc}</td>
                  <td>{zrosts.consId}</td>
                  <td>{zrosts.consName}</td>
                  <td>{zrosts.authPerson}</td>
                  <td>{zrosts.soId}</td>
                  <td>{zrosts.poId}</td>
                  <td>{zrosts.delivery}</td>
                  <td>{zrosts.grant}</td>
                  <td>{zrosts.wbs}</td>
                  <td>{zrosts.pickStatus}</td>
                  <td>{zrosts.toNumber}</td>
                  <td>{zrosts.trsptStatus}</td>
                  <td>{zrosts.waybId}</td>
                  <td>{zrosts.trsptrName}</td>
                  <td>{zrosts.shipmtEd ? <TextFormat type="date" value={zrosts.shipmtEd} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{zrosts.gdsStatus}</td>
                  <td>{zrosts.gdsDate ? <TextFormat type="date" value={zrosts.gdsDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{zrosts.roSubitem}</td>
                  <td>{zrosts.roType}</td>
                  <td>{zrosts.unit}</td>
                  <td>{zrosts.movingPrice}</td>
                  <td>{zrosts.plantId}</td>
                  <td>{zrosts.plantName}</td>
                  <td>{zrosts.storageLocp}</td>
                  <td>{zrosts.dwhId}</td>
                  <td>{zrosts.dwhDesc}</td>
                  <td>{zrosts.shipParty}</td>
                  <td>{zrosts.trsptMeans}</td>
                  <td>{zrosts.progOfficer}</td>
                  <td>{zrosts.soItems}</td>
                  <td>{zrosts.poItems}</td>
                  <td>{zrosts.trsptrId}</td>
                  <td>{zrosts.gdsId}</td>
                  <td>{zrosts.gdsItem}</td>
                  <td>{zrosts.batch}</td>
                  <td>{zrosts.bbDate ? <TextFormat type="date" value={zrosts.bbDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{zrosts.planningDate ? <TextFormat type="date" value={zrosts.planningDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{zrosts.checkinDate ? <TextFormat type="date" value={zrosts.checkinDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{zrosts.shipmentSdate ? <TextFormat type="date" value={zrosts.shipmentSdate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{zrosts.loadingSdate ? <TextFormat type="date" value={zrosts.loadingSdate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{zrosts.loadingEdate ? <TextFormat type="date" value={zrosts.loadingEdate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>
                    {zrosts.ashipmentSdate ? <TextFormat type="date" value={zrosts.ashipmentSdate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{zrosts.shipmentCdate ? <TextFormat type="date" value={zrosts.shipmentCdate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{zrosts.weight}</td>
                  <td>{zrosts.volume}</td>
                  <td>{zrosts.section}</td>
                  <td>{zrosts.commodityGroup}</td>
                  <td>{zrosts.region}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/zrosts/${zrosts.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/zrosts/${zrosts.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/zrosts/${zrosts.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="myTrackitApp.zrosts.home.notFound">No Zrosts found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Zrosts;
