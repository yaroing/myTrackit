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
import { IAction } from 'app/shared/model/action.model';
import { getEntity, updateEntity, createEntity, reset } from './action.reducer';

export const ActionUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const transferts = useAppSelector(state => state.mytrackit.transfert.entities);
  const actionEntity = useAppSelector(state => state.mytrackit.action.entity);
  const loading = useAppSelector(state => state.mytrackit.action.loading);
  const updating = useAppSelector(state => state.mytrackit.action.updating);
  const updateSuccess = useAppSelector(state => state.mytrackit.action.updateSuccess);

  const handleClose = () => {
    navigate('/action');
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
    values.dateAction = convertDateTimeToServer(values.dateAction);

    const entity = {
      ...actionEntity,
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
          dateAction: displayDefaultDateTime(),
        }
      : {
          ...actionEntity,
          dateAction: convertDateTimeFromServer(actionEntity.dateAction),
          transfert: actionEntity?.transfert?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="myTrackitApp.action.home.createOrEditLabel" data-cy="ActionCreateUpdateHeading">
            <Translate contentKey="myTrackitApp.action.home.createOrEditLabel">Create or edit a Action</Translate>
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
                  id="action-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('myTrackitApp.action.dateAction')}
                id="action-dateAction"
                name="dateAction"
                data-cy="dateAction"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('myTrackitApp.action.rapportAction')}
                id="action-rapportAction"
                name="rapportAction"
                data-cy="rapportAction"
                type="textarea"
              />
              <ValidatedField
                id="action-transfert"
                name="transfert"
                data-cy="transfert"
                label={translate('myTrackitApp.action.transfert')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/action" replace color="info">
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

export default ActionUpdate;
