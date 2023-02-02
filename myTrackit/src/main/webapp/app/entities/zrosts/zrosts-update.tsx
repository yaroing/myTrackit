import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IZrosts } from 'app/shared/model/zrosts.model';
import { getEntity, updateEntity, createEntity, reset } from './zrosts.reducer';

export const ZrostsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const zrostsEntity = useAppSelector(state => state.mytrackit.zrosts.entity);
  const loading = useAppSelector(state => state.mytrackit.zrosts.loading);
  const updating = useAppSelector(state => state.mytrackit.zrosts.updating);
  const updateSuccess = useAppSelector(state => state.mytrackit.zrosts.updateSuccess);

  const handleClose = () => {
    navigate('/zrosts');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.roDate = convertDateTimeToServer(values.roDate);
    values.roTdd = convertDateTimeToServer(values.roTdd);
    values.shipmtEd = convertDateTimeToServer(values.shipmtEd);
    values.gdsDate = convertDateTimeToServer(values.gdsDate);
    values.bbDate = convertDateTimeToServer(values.bbDate);
    values.planningDate = convertDateTimeToServer(values.planningDate);
    values.checkinDate = convertDateTimeToServer(values.checkinDate);
    values.shipmentSdate = convertDateTimeToServer(values.shipmentSdate);
    values.loadingSdate = convertDateTimeToServer(values.loadingSdate);
    values.loadingEdate = convertDateTimeToServer(values.loadingEdate);
    values.ashipmentSdate = convertDateTimeToServer(values.ashipmentSdate);
    values.shipmentCdate = convertDateTimeToServer(values.shipmentCdate);

    const entity = {
      ...zrostsEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          roDate: displayDefaultDateTime(),
          roTdd: displayDefaultDateTime(),
          shipmtEd: displayDefaultDateTime(),
          gdsDate: displayDefaultDateTime(),
          bbDate: displayDefaultDateTime(),
          planningDate: displayDefaultDateTime(),
          checkinDate: displayDefaultDateTime(),
          shipmentSdate: displayDefaultDateTime(),
          loadingSdate: displayDefaultDateTime(),
          loadingEdate: displayDefaultDateTime(),
          ashipmentSdate: displayDefaultDateTime(),
          shipmentCdate: displayDefaultDateTime(),
        }
      : {
          ...zrostsEntity,
          roDate: convertDateTimeFromServer(zrostsEntity.roDate),
          roTdd: convertDateTimeFromServer(zrostsEntity.roTdd),
          shipmtEd: convertDateTimeFromServer(zrostsEntity.shipmtEd),
          gdsDate: convertDateTimeFromServer(zrostsEntity.gdsDate),
          bbDate: convertDateTimeFromServer(zrostsEntity.bbDate),
          planningDate: convertDateTimeFromServer(zrostsEntity.planningDate),
          checkinDate: convertDateTimeFromServer(zrostsEntity.checkinDate),
          shipmentSdate: convertDateTimeFromServer(zrostsEntity.shipmentSdate),
          loadingSdate: convertDateTimeFromServer(zrostsEntity.loadingSdate),
          loadingEdate: convertDateTimeFromServer(zrostsEntity.loadingEdate),
          ashipmentSdate: convertDateTimeFromServer(zrostsEntity.ashipmentSdate),
          shipmentCdate: convertDateTimeFromServer(zrostsEntity.shipmentCdate),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="myTrackitApp.zrosts.home.createOrEditLabel" data-cy="ZrostsCreateUpdateHeading">
            <Translate contentKey="myTrackitApp.zrosts.home.createOrEditLabel">Create or edit a Zrosts</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="zrosts-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('myTrackitApp.zrosts.roId')} id="zrosts-roId" name="roId" data-cy="roId" type="text" />
              <ValidatedField
                label={translate('myTrackitApp.zrosts.roItem')}
                id="zrosts-roItem"
                name="roItem"
                data-cy="roItem"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.zrosts.roDate')}
                id="zrosts-roDate"
                name="roDate"
                data-cy="roDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('myTrackitApp.zrosts.roTdd')}
                id="zrosts-roTdd"
                name="roTdd"
                data-cy="roTdd"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('myTrackitApp.zrosts.materialId')}
                id="zrosts-materialId"
                name="materialId"
                data-cy="materialId"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.zrosts.matDesc')}
                id="zrosts-matDesc"
                name="matDesc"
                data-cy="matDesc"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.zrosts.delQty')}
                id="zrosts-delQty"
                name="delQty"
                data-cy="delQty"
                type="text"
              />
              <ValidatedField label={translate('myTrackitApp.zrosts.value')} id="zrosts-value" name="value" data-cy="value" type="text" />
              <ValidatedField
                label={translate('myTrackitApp.zrosts.storageLoc')}
                id="zrosts-storageLoc"
                name="storageLoc"
                data-cy="storageLoc"
                type="text"
              />
              <ValidatedField label={translate('myTrackitApp.zrosts.whId')} id="zrosts-whId" name="whId" data-cy="whId" type="text" />
              <ValidatedField
                label={translate('myTrackitApp.zrosts.whDesc')}
                id="zrosts-whDesc"
                name="whDesc"
                data-cy="whDesc"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.zrosts.consId')}
                id="zrosts-consId"
                name="consId"
                data-cy="consId"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.zrosts.consName')}
                id="zrosts-consName"
                name="consName"
                data-cy="consName"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.zrosts.authPerson')}
                id="zrosts-authPerson"
                name="authPerson"
                data-cy="authPerson"
                type="text"
              />
              <ValidatedField label={translate('myTrackitApp.zrosts.soId')} id="zrosts-soId" name="soId" data-cy="soId" type="text" />
              <ValidatedField label={translate('myTrackitApp.zrosts.poId')} id="zrosts-poId" name="poId" data-cy="poId" type="text" />
              <ValidatedField
                label={translate('myTrackitApp.zrosts.delivery')}
                id="zrosts-delivery"
                name="delivery"
                data-cy="delivery"
                type="text"
              />
              <ValidatedField label={translate('myTrackitApp.zrosts.grant')} id="zrosts-grant" name="grant" data-cy="grant" type="text" />
              <ValidatedField label={translate('myTrackitApp.zrosts.wbs')} id="zrosts-wbs" name="wbs" data-cy="wbs" type="text" />
              <ValidatedField
                label={translate('myTrackitApp.zrosts.pickStatus')}
                id="zrosts-pickStatus"
                name="pickStatus"
                data-cy="pickStatus"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.zrosts.toNumber')}
                id="zrosts-toNumber"
                name="toNumber"
                data-cy="toNumber"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.zrosts.trsptStatus')}
                id="zrosts-trsptStatus"
                name="trsptStatus"
                data-cy="trsptStatus"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.zrosts.waybId')}
                id="zrosts-waybId"
                name="waybId"
                data-cy="waybId"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.zrosts.trsptrName')}
                id="zrosts-trsptrName"
                name="trsptrName"
                data-cy="trsptrName"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.zrosts.shipmtEd')}
                id="zrosts-shipmtEd"
                name="shipmtEd"
                data-cy="shipmtEd"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('myTrackitApp.zrosts.gdsStatus')}
                id="zrosts-gdsStatus"
                name="gdsStatus"
                data-cy="gdsStatus"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.zrosts.gdsDate')}
                id="zrosts-gdsDate"
                name="gdsDate"
                data-cy="gdsDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('myTrackitApp.zrosts.roSubitem')}
                id="zrosts-roSubitem"
                name="roSubitem"
                data-cy="roSubitem"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.zrosts.roType')}
                id="zrosts-roType"
                name="roType"
                data-cy="roType"
                type="text"
              />
              <ValidatedField label={translate('myTrackitApp.zrosts.unit')} id="zrosts-unit" name="unit" data-cy="unit" type="text" />
              <ValidatedField
                label={translate('myTrackitApp.zrosts.movingPrice')}
                id="zrosts-movingPrice"
                name="movingPrice"
                data-cy="movingPrice"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.zrosts.plantId')}
                id="zrosts-plantId"
                name="plantId"
                data-cy="plantId"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.zrosts.plantName')}
                id="zrosts-plantName"
                name="plantName"
                data-cy="plantName"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.zrosts.storageLocp')}
                id="zrosts-storageLocp"
                name="storageLocp"
                data-cy="storageLocp"
                type="text"
              />
              <ValidatedField label={translate('myTrackitApp.zrosts.dwhId')} id="zrosts-dwhId" name="dwhId" data-cy="dwhId" type="text" />
              <ValidatedField
                label={translate('myTrackitApp.zrosts.dwhDesc')}
                id="zrosts-dwhDesc"
                name="dwhDesc"
                data-cy="dwhDesc"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.zrosts.shipParty')}
                id="zrosts-shipParty"
                name="shipParty"
                data-cy="shipParty"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.zrosts.trsptMeans')}
                id="zrosts-trsptMeans"
                name="trsptMeans"
                data-cy="trsptMeans"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.zrosts.progOfficer')}
                id="zrosts-progOfficer"
                name="progOfficer"
                data-cy="progOfficer"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.zrosts.soItems')}
                id="zrosts-soItems"
                name="soItems"
                data-cy="soItems"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.zrosts.poItems')}
                id="zrosts-poItems"
                name="poItems"
                data-cy="poItems"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.zrosts.trsptrId')}
                id="zrosts-trsptrId"
                name="trsptrId"
                data-cy="trsptrId"
                type="text"
              />
              <ValidatedField label={translate('myTrackitApp.zrosts.gdsId')} id="zrosts-gdsId" name="gdsId" data-cy="gdsId" type="text" />
              <ValidatedField
                label={translate('myTrackitApp.zrosts.gdsItem')}
                id="zrosts-gdsItem"
                name="gdsItem"
                data-cy="gdsItem"
                type="text"
              />
              <ValidatedField label={translate('myTrackitApp.zrosts.batch')} id="zrosts-batch" name="batch" data-cy="batch" type="text" />
              <ValidatedField
                label={translate('myTrackitApp.zrosts.bbDate')}
                id="zrosts-bbDate"
                name="bbDate"
                data-cy="bbDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('myTrackitApp.zrosts.planningDate')}
                id="zrosts-planningDate"
                name="planningDate"
                data-cy="planningDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('myTrackitApp.zrosts.checkinDate')}
                id="zrosts-checkinDate"
                name="checkinDate"
                data-cy="checkinDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('myTrackitApp.zrosts.shipmentSdate')}
                id="zrosts-shipmentSdate"
                name="shipmentSdate"
                data-cy="shipmentSdate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('myTrackitApp.zrosts.loadingSdate')}
                id="zrosts-loadingSdate"
                name="loadingSdate"
                data-cy="loadingSdate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('myTrackitApp.zrosts.loadingEdate')}
                id="zrosts-loadingEdate"
                name="loadingEdate"
                data-cy="loadingEdate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('myTrackitApp.zrosts.ashipmentSdate')}
                id="zrosts-ashipmentSdate"
                name="ashipmentSdate"
                data-cy="ashipmentSdate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('myTrackitApp.zrosts.shipmentCdate')}
                id="zrosts-shipmentCdate"
                name="shipmentCdate"
                data-cy="shipmentCdate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('myTrackitApp.zrosts.weight')}
                id="zrosts-weight"
                name="weight"
                data-cy="weight"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.zrosts.volume')}
                id="zrosts-volume"
                name="volume"
                data-cy="volume"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.zrosts.section')}
                id="zrosts-section"
                name="section"
                data-cy="section"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.zrosts.commodityGroup')}
                id="zrosts-commodityGroup"
                name="commodityGroup"
                data-cy="commodityGroup"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.zrosts.region')}
                id="zrosts-region"
                name="region"
                data-cy="region"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/zrosts" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ZrostsUpdate;
