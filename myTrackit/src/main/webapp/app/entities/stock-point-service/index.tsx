import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import StockPointService from './stock-point-service';
import StockPointServiceDetail from './stock-point-service-detail';
import StockPointServiceUpdate from './stock-point-service-update';
import StockPointServiceDeleteDialog from './stock-point-service-delete-dialog';

const StockPointServiceRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<StockPointService />} />
    <Route path="new" element={<StockPointServiceUpdate />} />
    <Route path=":id">
      <Route index element={<StockPointServiceDetail />} />
      <Route path="edit" element={<StockPointServiceUpdate />} />
      <Route path="delete" element={<StockPointServiceDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default StockPointServiceRoutes;
