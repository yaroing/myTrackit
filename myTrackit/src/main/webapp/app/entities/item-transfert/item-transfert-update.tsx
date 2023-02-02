import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITransfert } from 'app/shared/model/transfert.model';
import { getEntities as getTransferts } from 'app/entities/transfert/transfert.reducer';
import { IItemTransfert } from 'app/shared/model/item-transfert.model';
import { getEntity, updateEntity, createEntity, reset } from './item-transfert.reducer';

export const ItemTransfertUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const transferts = useAppSelector(state => state.mytrackit.transfert.entities);
  const itemTransfertEntity = useAppSelector(state => state.mytrackit.itemTransfert.entity);
  const loading = useAppSelector(state => state.mytrackit.itemTransfert.loading);
  const updating = useAppSelector(state => state.mytrackit.itemTransfert.updating);
  const updateSuccess = useAppSelector(state => state.mytrackit.itemTransfert.updateSuccess);

  const handleClose = () => {
    navigate('/item-transfert');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getTransferts({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.roDate = convertDateTimeToServer(values.roDate);
    values.bbDate = convertDateTimeToServer(values.bbDate);

    const entity = {
      ...itemTransfertEntity,
      ...values,
      transfert: transferts.find(it => it.id.toString() === values.transfert.toString()),
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
          bbDate: displayDefaultDateTime(),
        }
      : {
          ...itemTransfertEntity,
          roDate: convertDateTimeFromServer(itemTransfertEntity.roDate),
          bbDate: convertDateTimeFromServer(itemTransfertEntity.bbDate),
          transfert: itemTransfertEntity?.transfert?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="myTrackitApp.itemTransfert.home.createOrEditLabel" data-cy="ItemTransfertCreateUpdateHeading">
            <Translate contentKey="myTrackitApp.itemTransfert.home.createOrEditLabel">Create or edit a ItemTransfert</Translate>
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
                  id="item-transfert-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('myTrackitApp.itemTransfert.roDate')}
                id="item-transfert-roDate"
                name="roDate"
                data-cy="roDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('myTrackitApp.itemTransfert.matDesc')}
                id="item-transfert-matDesc"
                name="matDesc"
                data-cy="matDesc"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.itemTransfert.unit')}
                id="item-transfert-unit"
                name="unit"
                data-cy="unit"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.itemTransfert.delQty')}
                id="item-transfert-delQty"
                name="delQty"
                data-cy="delQty"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.itemTransfert.value')}
                id="item-transfert-value"
                name="value"
                data-cy="value"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.itemTransfert.batch')}
                id="item-transfert-batch"
                name="batch"
                data-cy="batch"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.itemTransfert.bbDate')}
                id="item-transfert-bbDate"
                name="bbDate"
                data-cy="bbDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('myTrackitApp.itemTransfert.weight')}
                id="item-transfert-weight"
                name="weight"
                data-cy="weight"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.itemTransfert.volume')}
                id="item-transfert-volume"
                name="volume"
                data-cy="volume"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.itemTransfert.recQty')}
                id="item-transfert-recQty"
                name="recQty"
                data-cy="recQty"
                type="text"
              />
              <ValidatedField
                id="item-transfert-transfert"
                name="transfert"
                data-cy="transfert"
                label={translate('myTrackitApp.itemTransfert.transfert')}
                type="select"
              >
                <option value="" key="0" />
                {transferts
                  ? transferts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/item-transfert" replace color="info">
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

export default ItemTransfertUpdate;
