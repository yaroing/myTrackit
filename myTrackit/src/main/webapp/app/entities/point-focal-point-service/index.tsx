import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PointFocalPointService from './point-focal-point-service';
import PointFocalPointServiceDetail from './point-focal-point-service-detail';
import PointFocalPointServiceUpdate from './point-focal-point-service-update';
import PointFocalPointServiceDeleteDialog from './point-focal-point-service-delete-dialog';

const PointFocalPointServiceRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PointFocalPointService />} />
    <Route path="new" element={<PointFocalPointServiceUpdate />} />
    <Route path=":id">
      <Route index element={<PointFocalPointServiceDetail />} />
      <Route path="edit" element={<PointFocalPointServiceUpdate />} />
      <Route path="delete" element={<PointFocalPointServiceDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PointFocalPointServiceRoutes;
