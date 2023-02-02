import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPointFocalPartenaire } from 'app/shared/model/point-focal-partenaire.model';
import { getEntity, updateEntity, createEntity, reset } from './point-focal-partenaire.reducer';

export const PointFocalPartenaireUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const pointFocalPartenaireEntity = useAppSelector(state => state.mytrackit.pointFocalPartenaire.entity);
  const loading = useAppSelector(state => state.mytrackit.pointFocalPartenaire.loading);
  const updating = useAppSelector(state => state.mytrackit.pointFocalPartenaire.updating);
  const updateSuccess = useAppSelector(state => state.mytrackit.pointFocalPartenaire.updateSuccess);

  const handleClose = () => {
    navigate('/point-focal-partenaire');
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
    const entity = {
      ...pointFocalPartenaireEntity,
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
      ? {}
      : {
          ...pointFocalPartenaireEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="myTrackitApp.pointFocalPartenaire.home.createOrEditLabel" data-cy="PointFocalPartenaireCreateUpdateHeading">
            <Translate contentKey="myTrackitApp.pointFocalPartenaire.home.createOrEditLabel">
              Create or edit a PointFocalPartenaire
            </Translate>
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
                  id="point-focal-partenaire-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('myTrackitApp.pointFocalPartenaire.nomPf')}
                id="point-focal-partenaire-nomPf"
                name="nomPf"
                data-cy="nomPf"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.pointFocalPartenaire.fonctionPf')}
                id="point-focal-partenaire-fonctionPf"
                name="fonctionPf"
                data-cy="fonctionPf"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.pointFocalPartenaire.gsmPf')}
                id="point-focal-partenaire-gsmPf"
                name="gsmPf"
                data-cy="gsmPf"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.pointFocalPartenaire.emailPf')}
                id="point-focal-partenaire-emailPf"
                name="emailPf"
                data-cy="emailPf"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/point-focal-partenaire" replace color="info">
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

export default PointFocalPartenaireUpdate;
